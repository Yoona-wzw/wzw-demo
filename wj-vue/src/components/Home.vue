<template>
<div>
  <NavMenu class="navmenu"></NavMenu>
  <router-view></router-view>
</div>
</template>

<script>
    import NavMenu from "./common/NavMenu";
    export default {
        name: "Home",
        components: {NavMenu}
    }
</script>

<style scoped>
.navmenu{
}
  .player{
    position:absolute;
    top:8%;
    height:80%;
  }
</style>
