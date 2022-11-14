<template>
  <div class="container-fluid">
    <div class="row flex-nowrap">
      <div class="col-auto col-md-3 col-xl-2 px-sm-4 px-0 bg-dark">
        <div
          class="
            d-flex
            flex-column
            align-items-center align-items-sm-start
            px-3
            pt-2
            text-white
            min-vh-100
            sticky-top
          "
        >
          <a
            href="#"
            class="
              d-flex
              align-items-center
              pb-3
              mb-md-0
              me-md-auto
              text-white text-decoration-none
            "
          >
            <span class="fs-5 d-none d-sm-inline">Pass Booking System</span>
          </a>
          <ul
            class="
              nav nav-pills
              flex-column
              mb-sm-auto mb-0
              align-items-center align-items-sm-start
              sidebar
            "
            id="menu"
          >
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle text-decoration-none">
                <router-link to="/gop/booking/view" class="text-decoration-none">
                  <i class="fs-4 bi-calendar-week me-1"></i>
                  <span class="d-none d-sm-inline ms-3">Booking</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle text-decoration-none">
                <router-link to="/gop/booking/manage" class="text-decoration-none">
                  <i class="fs-4 bi-calendar-check me-1"></i>
                  <span class="d-none d-sm-inline ms-3">Manage</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/gop/passes" class="text-decoration-none">
                  <i class="fs-4 bi bi-pass"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3">Passes</span>
                </router-link>
              </a>
            </li>
          </ul>
          <hr />
          <div class="dropdown pb-4">
            <a
              href="#"
              class="
                d-flex
                align-items-center
                text-white text-decoration-none
                dropdown-toggle
              "
              id="dropdownUser1"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img
                src="https://github.com/mdo.png"
                alt="hugenerd"
                width="30"
                height="30"
                class="rounded-circle"
              />
              <span class="d-none d-sm-inline mx-1">{{ user }}</span>
            </a>
            <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
              <li><a class="dropdown-item" href="#">New project...</a></li>
              <li><a class="dropdown-item" href="#">Settings</a></li>
              <li><a class="dropdown-item" href="#">Profile</a></li>
              <li>
                <hr class="dropdown-divider" />
              </li>
              <li @click="logout">
                <a class="dropdown-item" href="#">
                  <router-link to="/login" class="text-decoration-none">
                    Sign out
                  </router-link>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="col">
        <div class="router-outlet">
          <router-view />
        </div>
      </div>
    </div>
  </div>
  <!-- https://dev.to/codeply/bootstrap-5-sidebar-examples-38pb -->
</template>

<script>

export default {
  name: "SidebarGOP",
  data(){
    return {
      user: ""
    }
  },
  mounted(){
    this.user = localStorage.getItem("userType");
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      this.$router.replace('/login');
    }
  }

}
</script>

<style scoped>

.col {
  padding: 0 !important;
}
.router-outlet {
  background-color: aliceblue;
  min-height: 100vh;
}
</style>
