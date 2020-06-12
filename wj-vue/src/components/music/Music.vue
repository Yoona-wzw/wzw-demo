<template>

  <div class="music-div">
    <el-backtop target=".music-div" :visibility-height="100"><div>Top</div></el-backtop>
    <el-row class="mcrow" type="fixed">
      <el-col :xs="4" :sm="4" :md="4" :lg="4">
        <el-menu
          default-active="0"
          class="el-menu-vertical-demo"
          @select="handleOpen"
        >
          <el-menu-item index="0">
            <span slot="title">新歌榜</span>
          </el-menu-item>
          <el-menu-item index="1">
            <span slot="title">热歌榜</span>
          </el-menu-item>
          <el-menu-item index="3">
            <span slot="title">飙升榜</span>
          </el-menu-item>
          <el-menu-item index="17">
            <span slot="title">华语金曲榜</span>
          </el-menu-item>
          <el-menu-item index="15">
            <span slot="title">中国TOP排行榜(内地榜)</span>
          </el-menu-item>
          <el-menu-item index="16">
            <span slot="title">香港电台中文歌曲龙虎榜</span>
          </el-menu-item>
          <el-menu-item index="33">
          </el-menu-item>
          <el-menu-item index="34">
          </el-menu-item>
          <el-menu-item index="35">
          </el-menu-item>
          <el-menu-item index="36">
          </el-menu-item>
          <el-menu-item index="37">
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :xs="15" :sm="15" :md="15" :lg="15" class="el-table">
        <div>
        <el-input @keyup.enter.native="searchMusic" placeholder="通过歌手名或歌曲名搜索"
                  prefix-icon="el-icon-search" size="small" style="width:300px;margin-right:10px;" v-model="key"
                  clearable>

        </el-input>
        <el-button size="small" type="primary" icon="el-icon-search" @click="searchMusic">搜索</el-button>
        </div>
        <div id="mslist"><b style="font-size:18px;">歌曲列表</b>&nbsp;&nbsp;<span>{{sum}}首歌</span>&nbsp;&nbsp;<el-button type="primary" size="small" @click="playall">播放全部<i class="el-icon-plus"></i></el-button></div>
        <el-table
          :data="musics"
          stripe
          style="width:85%;margin-left:50px;border:1px solid #e6e6e6;margin-top:10%;border-top:2px solid #c20c0c;"
        >
          <el-table-column prop="title" label="标题">
            <template slot-scope="scope">
              <span v-if="scope.row.pic!=''">
              <img :src="scope.row.pic" style="width:30px;height:30px;line-height: 30px;">
              </span>
              <i class="el-icon-video-play" style="cursor: pointer;" @click="playMs(scope.row)"></i>
              <span style="margin-left:10px;">{{scope.row.title}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="artist" label="歌手"></el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <div class="div-play">
      <aplayer autoplay
               :music="musicInfo"
               :list="playList"
               :showLrc="true"
               :listFolded="true"
               :listMaxHeight="'200px'"
               ref="child"
      ></aplayer>
    </div>


    </div>
</template>

<script>
  import aplayer from 'vue-aplayer'

  aplayer.disableVersionBadge = true
  export default {
    name: "Music",
    components: {
      aplayer
    },
    data() {
      return {
        msUrl: '',
        musics: [],
        playList: [],
        key: '',
        sum: '',
        page: 1,
        isShow: false,
        mini: false,
        musicInfo: {
          title: '海阔天空',
          artist: 'Beyond',
          src: '/static/21.mp3',
          pic: 'http://p1.music.126.net/Umy9RllboQslHMGkPlPVOA==/109951163720162979.jpg?param=300y300',
          lrc: ''
        },
      }
    },
    mounted() {
       this.searchClick()
    },
    methods: {
       searchClick() {
        this.$axios.get('/music/searchMusic?type=0').then(resp => {

          this.musics = resp.data
          this.sum = this.musics.length + ''
          this.isShow = true
          this.playList.push(this.musicInfo)
        })
      },
      handleOpen() {

      },
      handleClose() {

      },
      playMs(row) {
        var id = row.id
        var title = row.title
        var artist = row.artist
        var pic = row.pic
        var url
        var lrc
        var plist = {
          title: '',
          artist: '',
          src: '',
          pic: '',
          lrc: ''
        }
        this.$axios.get('/music/searchUrlLyr?id=' + id).then(resp => {
          url = resp.data.url
          lrc = resp.data.lrc
          plist.title = title
          plist.artist = artist
          plist.pic = pic
          plist.src = url
          plist.lrc = lrc
          for (var i in this.playList) {
            if (this.playList[i].title == title) {
              this.$refs.child.onSelectSong(plist)
              return false
            }
          }
          this.playList.unshift(plist)
          this.$refs.child.onSelectSong(plist)
        })
      },
      handleOpen(index) {
        this.$axios.get('/music/searchMusic?type=' + index).then(resp => {
          this.musics = resp.data
          this.sum = this.musics.length + ''
        })
      },
      searchMusic() {
        this.$axios.get('/music/searchKey?key=' + this.key).then(resp => {
          this.musics = resp.data
          this.sum = this.musics.length + ''
        })
      },
      playall(){
        this.playList.shift();
        for(let p in this.musics){
          let plist= {
            title: '',
            artist: '',
            src: '',
            pic: '',
            lrc: ''
          }
          let sid=this.musics[p].id
          this.$axios.get('/music/searchUrlLyr?id=' + sid).then(resp => {
            plist.src = resp.data.url
            plist.lrc = resp.data.lrc
            plist.title=this.musics[p].title
            plist.artist=this.musics[p].artist
            plist.pic=this.musics[p].pic
            this.playList.push(plist)
            if(p==0){
              this.$refs.child.onSelectSong(plist)
            }
          })


        }
      }

    }
  }
</script>

<style scoped>
  .el-menu-vertical-demo {
    width: 200px;
    height: 100%;
    margin-left: 50px;
    border-left: 1px solid #e6e6e6;
    border-right: 1px solid #e6e6e6;
  }

  .div-play {
    position: fixed;
    bottom: 0px;
    margin: 0px;
    padding: 0px;
    width: 100%;
  }

  .el-table {
    border-right: 1px solid #e6e6e6;
    margin-left: 50px;
  }

  .music-div {
    margin-top: 3%;
  }

  #mslist {
    position: relative;
   top:40px;
    float: left;
    left: 50px;
  }

  .mcrow {
  }
</style>
