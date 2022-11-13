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
            <!-- <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/" class="text-decoration-none">
                  <i class="fs-4 bi bi-house"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3">Home</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/dashboard" class="text-decoration-none">
                  <i class="fs-4 bi bi-graph-up"></i>
                  <span class="d-none d-sm-inline ms-3">Dashboard</span>
                </router-link>
              </a>
            </li> -->
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/booking/view" class="text-decoration-none">
                  <i class="fs-4 bi-calendar-week me-1"></i>
                  <span class="d-none d-sm-inline ms-3">Booking</span>
                </router-link>
              </a>
            </li>
            <!-- <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/booking/manage" class="text-decoration-none">
                  <i class="fs-4 bi-calendar-check me-1"></i>
                  <span class="d-none d-sm-inline ms-3">Manage</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/employee" class="text-decoration-none">
                  <i class="fs-4 bi bi-book"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3">Employee</span>
                </router-link>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/pass" class="text-decoration-none">
                  <i class="fs-4 bi bi-pass"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3">Pass</span>
                </router-link>
              </a>
            </li> -->
            <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/passUser" class="text-decoration-none">
                  <i class="fs-4 bi bi-pass"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3">PassUser</span>
                </router-link>
              </a>
            </li>
            <!-- <li class="nav-item">
              <a href="#" class="nav-link link-dark px-0 align-middle">
                <router-link to="/email" class="text-decoration-none">
                  <i class="fs-4 bi bi-envelope"></i>
                  <span class="ms-1 d-none d-sm-inline ms-3">Email</span>
                </router-link>
              </a>
            </li> -->
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
                  <router-link to="/" class="text-decoration-none">
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
// import { router } from 'vue-router'

export default {
  name: "Sidebar",
  // computed: {
  //   userLoggedIn() {
  //     alert("yes")
  //     return localStorage.getItem("userLoggedIn")
  // }
  // },
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
/* body, html {
  min-height: 100vh;
  min-height: -webkit-fill-available;
  padding: 0;
  margin: 0;
} 




main {
  height: 100vh;
  height: -webkit-fill-available;
  max-height: 100vh;
  overflow-x: auto;
  overflow-y: hidden;
}

.dropdown-toggle { outline: 0; }

.btn-toggle {
  padding: .25rem .5rem;
  font-weight: 600;
  color: rgba(0, 0, 0, .65);
  background-color: transparent;
}
.btn-toggle:hover,
.btn-toggle:focus {
  color: rgba(0, 0, 0, .85);
  background-color: #d2f4ea;
}

.btn-toggle::before {
  width: 1.25em;
  line-height: 0;
  content: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='rgba%280,0,0,.5%29' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M5 14l6-6-6-6'/%3e%3c/svg%3e");
  transition: transform .35s ease;
  transform-origin: .5em 50%;
}

.btn-toggle[aria-expanded="true"] {
  color: rgba(0, 0, 0, .85);
}
.btn-toggle[aria-expanded="true"]::before {
  transform: rotate(90deg);
}

.btn-toggle-nav a {
  padding: .1875rem .5rem;
  margin-top: .125rem;
  margin-left: 1.25rem;
}
.btn-toggle-nav a:hover,
.btn-toggle-nav a:focus {
  background-color: #d2f4ea;
}

.scrollarea {
  overflow-y: auto;
} */

.col {
  padding: 0 !important;
}
.router-outlet {
  background-color: aliceblue;
  min-height: 100vh;
}
</style>
