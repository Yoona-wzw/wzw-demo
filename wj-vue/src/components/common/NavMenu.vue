<template>
  <el-row type="flex" align="middle" class="row-bg">
    <el-col :xs="8" :sm="8" :md="8" :lg="8">
      <el-menu
        :default-active="'/index'"
        router
        mode="horizontal"
        background-color="white"
        text-color="#222"
        active-text-color="red"
        style="min-width: 100%;"
        class="nav_menu"
      >
        <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
          {{item.navItem}}
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :xs="10" :sm="10" :md="10" :lg="10">
          <span id="span1"
            style="font-weight: bold;font-family: Helvetica Neue;font-size: 20px;">White Jotter - Your Mind Palace</span>
    </el-col>
    <el-col :xs="4" :sm="4" :md="4" :lg="4">
      <el-dropdown>

        <a href="#" style="color: #222;">更多功能</a>
        <i class="el-icon-menu" > </i>
        <el-dropdown-menu slot="dropdown" class="edm">
          <el-dropdown-item><span @click="photos">壁纸</span></el-dropdown-item>
          <el-dropdown-item><span @click="music">音乐</span></el-dropdown-item>
        </el-dropdown-menu>

      </el-dropdown>
    </el-col>

    <el-col :xs="2" :sm="2" :md="2" :lg="2">
      <i class="el-icon-switch-button" @click="logout">退出</i>
    </el-col>
  </el-row>
</template>

<script>
  import Music from "../music/Music";
  import Photos from "../photos/Photos";
  import 'jQuery/tmp/jquery';

  export default {
    name: "NavMenu",
    components: {Photos, Music},
    data() {
      return {
        dialogVisible: false,
        navList: [
          {name: '/index', navItem: '首页'},
          {name: '/jotter', navItem: '笔记本'},
          {name: '/library', navItem: '图书'},
          {name: '/admin', navItem: '个人中心'}

        ]
      }
    },
    methods: {
      logout() {
        this.$confirm('要离开了嘛', '', {
          confirmButtonText: '离开',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.post('/logout').then(resp => {
            if (resp.status == 200) {
              this.$store.commit('logout')
              this.$notify({
                message: resp.data.message,
                type: 'success'
              })
              this.$router.replace({path: '/login'})
            }
          })
        }).catch(() => {

        })

      },
      photos() {
        this.$router.replace({path: '/photos'})
      },
      music() {
        this.$router.replace({path: '/music'})
      }
    }
  }
</script>

<style scoped>
  .nav_menu {
    position: relative;
  }

  .el-icon-switch-button {

    cursor: pointer;
  }

  a {
    text-decoration: none;
  }
  .row-bg {
    border-radius:4px;
  }
  .el-menu.el-menu--horizontal {
    border:0px;
  }
</style>
