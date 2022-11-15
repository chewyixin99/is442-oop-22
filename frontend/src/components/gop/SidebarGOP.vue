<template>
  <div class="container-fluid">
    <div class="row flex-nowrap">
      <div class="col-auto col-md-3 col-xl-2 px-sm-4 px-0 sidebar-body">
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
          <div class="d-flex justify-content-center pt-4">
            <img
              class="img-fluid mb-4 logo"
              width="200"
              src="../../assets/SSS_logo.png"
              alt=""
            />
          </div>
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
            <span class="fs-5 d-none d-sm-inline text-dark">Pass Booking System</span>
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
                  <i class="fs-4 bi-calendar-week me-1 sidebar-text"></i>
                  <span class="d-none d-sm-inline ms-3 sidebar-text">Booking</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle text-decoration-none">
                <router-link to="/gop/booking/manage" class="text-decoration-none">
                  <i class="fs-4 bi-calendar-check me-1 sidebar-text"></i>
                  <span class="d-none d-sm-inline ms-3 sidebar-text">Manage</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle text-decoration-none">
                <router-link to="/gop/passes" class="text-decoration-none">
                  <i class="fs-4 bi bi-pass sidebar-text"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3 sidebar-text">Passes</span>
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
            <ul class="dropdown-menu dropdown-menu-light text-small shadow">
              <li>
                <a class="dropdown-item text-decoration-none text-dark" href="#">
                  <i class="bi bi-gear-fill me-2"></i>Settings
                </a>
              </li>
              <li>
                <hr class="dropdown-divider" />
              </li>
              <li @click="logout">
                <a class="dropdown-item text-decoration-none text-dark" href="#">
                  <router-link to="/" class="text-decoration-none text-dark">
                    <i class="bi bi-box-arrow-right me-2"></i>Sign out
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
      this.$router.replace('/');
    }
  }

}
</script>

<style scoped>
.sidebar-body {
  background-color: var(--sss_red);
}

.sidebar-text {
  color: aliceblue;
  font-weight: 500;
}

.col {
  padding: 0 !important;
}
.router-outlet {
  background-color: aliceblue;
  min-height: 100vh;
}

.nav-item:hover{
  transform: scale(1.1);
}

@media screen and (max-width: 575px) {
  .logo {
    display: none;
  }
}
</style>
