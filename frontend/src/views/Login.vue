<template>
  <div class="page-holder align-items-center py-4 bg-gray-100 vh-100">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-lg-6 px-lg-4 mb-4" v-if="userlogin">
          <div class="card">
            <div class="card-header px-lg-5">
              <div class="card-heading text-dark"><h2>Login</h2></div>
            </div>
            <div class="card-body p-lg-5">
              <h3 class="mb-4">Hi, welcome back! 👋👋</h3>
              <p class="text-muted text-sm mb-5">
                This is the prototype for SSS's corporate booking system in
                collaboration with SMU. <br />
                Log in to get started!
              </p>
              <form id="loginForm" action="index.html">
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="floatingInput"
                    type="email"
                    v-model="email"
                    placeholder="name@example.com"
                    required
                  />
                  <label for="floatingInput">Email address</label>
                </div>
                <div class="form-floating mb-2">
                  <input
                    class="form-control"
                    id="floatingPassword"
                    type="password"
                    v-model="password"
                    placeholder="Password"
                    required
                  />
                  <label for="floatingPassword">Password</label>
                </div>
                <div class="text-sm text-end">
                  <a @click="user_forgotpassword()">Forgot password?</a>
                </div>
                <div class="form-check mb-3">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    name="remember"
                    id="remember"
                  />
                  <label class="form-check-label d-flex ms-1" for="remember"
                    >Remember me</label
                  >
                </div>
                <button
                  class="btn btn-primary"
                  type="button"
                  @click="login"
                  :disabled="!email || !password"
                >
                  Submit
                </button>
              </form>
            </div>
            <div class="card-footer px-lg-5 py-lg-4">
              <div class="text-sm text-muted">
                Don't have an account? <a @click="user_register()">Register</a>.
              </div>
            </div>
          </div>
        </div>
        <!-- register -->
        <div class="col-lg-6 px-lg-4" v-if="userregister">
          <div class="card">
            <div class="card-header px-lg-5">
              <div class="card-heading text-dark"><h2>Register</h2></div>
            </div>
            <div class="card-body p-lg-5">
              <h3 class="mb-4">No account? Simply sign up now!</h3>
              <p class="text-muted text-sm mb-5">
                After registering, you will receive your account credentials if
                your email is registered in our system!
              </p>
              <form action="index.html">
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="username"
                    type="email"
                    v-model="username"
                    placeholder="name@example.com"
                    required
                  />
                  <label for="username">Username</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="floatingInput"
                    type="email"
                    v-model="email"
                    placeholder="name@example.com"
                    required
                  />
                  <label for="floatingInput">Email address</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="contactNumber"
                    type="number"
                    v-model="contactNumber"
                    placeholder="Contact Number"
                    required
                  />
                  <label for="contactNumber">Contact Number</label>
                </div>
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="floatingPassword"
                    type="password"
                    v-model="password"
                    placeholder="Password"
                    required
                  />
                  <label for="floatingPassword">Password</label>
                </div>
                <div class="form-check mb-3">
                  <input
                    v-model="termsAndCondition"
                    class="form-check-input"
                    type="checkbox"
                    name="agree"
                    id="agree"
                  />
                  <label class="form-check-label d-flex ms-1" for="agree" 
                    >I agree with the
                    <a
                      target="_blank"
                      href="https://www.youtube.com/watch?v=xvFZjo5PgG0"
                      class="ms-1"
                      >Terms & Conditions</a
                    >.</label
                  >
                </div>
                <div class="form-group">
                  <button
                    v-if="!isRegisterButtonClicked"
                    class="btn btn-primary"
                    id="register"
                    type="button"
                    name="registerSubmit"
                    @click="register()"
                    :disabled="!termsAndCondition || !username || !email || !password || !contactNumber"
                  >
                    Register
                  </button>
                  <button
                    v-else
                    class="btn btn-primary"
                    id="reset"
                    type="button"
                    name="resetSubmit"
                    @click="user_login()"
                  >
                    Back to Login
                  </button>
                </div>
              </form>
            </div>
            <div class="card-footer px-lg-5 py-lg-4">
              <div class="text-sm text-muted">
                Already have an account? <a @click="user_login()">Login</a>.
              </div>
            </div>
          </div>
        </div>
        <!-- forgot password-->
        <div class="col-lg-6 px-lg-4" v-if="userforgotpassword">
          <div class="card">
            <div class="card-header px-lg-5">
              <div class="card-heading text-dark"><h2>Reset Password</h2></div>
            </div>
            <div class="card-body p-lg-5">
              <h3 class="mb-4">Lost your password? Don't panic!</h3>
              <p class="text-muted text-sm mb-5">
                It's happens to the best of us.. Just enter your email below and
                we'll send you an email to reset your password! Remember to
                check your spam if you don't receive it after a few minutes~
              </p>
              <form v-if="!isResetButtonEmailClicked" id="loginForm" action="index.html">
                <div class="form-floating mb-3">
                  <input
                    class="form-control"
                    id="floatingInput"
                    v-model="email"
                    type="email"
                    placeholder="name@example.com"
                    required
                  />
                  <label for="floatingInput">Email address</label>
                </div>
                <div class="form-group">
                  <button
                    class="btn btn-primary"
                    id="reset"
                    type="button"
                    name="resetSubmit"
                    @click="reset_password()"
                    :disabled="
                      email &&
                      (!email.includes('@') || !email.includes('.'))
                    "
                  >
                    Reset
                  </button>
                </div>
              </form>
              <div v-else>
                <div class="alert alert-warning border border-warning border-4" role="alert">
                  Check your inbox (<b>{{email}}</b>) for the next steps! If you don't receive an email, and it's not in your spam folder, then you might have signed up with a different address.
                </div>
                <p class="text-muted text-sm mb-5">
                  Still can't login? Feel free to email us at <a href="mailto:sport.singapore.helpdesk@gmail.com">sport.singapore.helpdesk@gmail.com</a> if you require additional assistance!
                </p>
              </div>

            </div>
            <div class="card-footer px-lg-5 py-lg-4">
              <div class="text-sm text-muted">
                Already have an account? <a @click="user_login()">Login</a>.
              </div>
              <div class="text-sm text-muted">
                Don't have an account? <a @click="user_register()">Register</a>.
              </div>
            </div>
          </div>
        </div>
        <div
          class="col-lg-6 col-xl-5 ms-xl-auto px-lg-4 text-center text-primary"
        >
          <img
            class="img-fluid mb-4"
            width="300"
            src="../assets/SSS_logo.png"
            alt=""
          />
          <h1 class="mb-4">Corporate Pass Booking System</h1>
          <p class="lead text-muted"><i>Time to book some fun!</i></p>
        </div>
      </div>
      <TheToastr :toastrResponse="toastrResponse"></TheToastr>
    </div>
  </div>
</template>

<script>
import { Toast } from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";
import axios from "axios";
import jwt_decode from "jwt-decode";
import LoginService from "@/api/services/LoginService";

// https://therichpost.com/vue-3-bootstrap-5-user-login-registration-forms-show-hide-on-button-click/

export default {
  name: "Login",
  components: {
    TheToastr,
  },
  data() {
    return {
      username: "test",
      email: "leeroy2@mailinator.com",
      password: "123",
      contactNumber: "91234567",
      userlogin: true,
      userregister: false,
      userforgotpassword: false,
      termsAndCondition: false,
      isResetButtonEmailClicked: false,
      toastrResponse: "",
      isRegisterButtonClicked: false,
    };
  },
  methods: {
    login() {
      // if (this.email.split("@")[0] == "admin" ) {
      //     localStorage.setItem("userType", "ADMIN")
      //     this.$router.replace('/admin');
      // }
      // else if (this.email.split("@")[0] == "gop") {
      //     localStorage.setItem("userType", "GOP")
      // }
      // else if (this.email.split("@")[0] == "borrower") {
      //     localStorage.setItem("userType", "BORROWER")
      //     this.$router.replace('/booking/view');
      // }
      // else {
      //     alert("Incorrect credentials! Please try again.")
      // }

      //login api call
      axios
        .post("http://localhost:8081/login", {
          email: this.email,
          password: this.password,
        })
        .then((response1) => {
          console.log(response1);
          if (response1.status == 200) {
            const bearer_token = `Bearer ${response1.data.data}`;
            const config = {
              headers: {
                Authorization: bearer_token,
              },
            };
            axios
              .get("http://localhost:8081/users", config)
              .then((response2) => {
                console.log(response2);
                if (response2.status == 200) {
                  const user = response2.data.data.find(
                    (user) => user.email === this.email
                  );
                  console.log(user);
                  localStorage.setItem("token", response1.data.data);
                  localStorage.setItem("user", JSON.stringify(user));
                  const decoded = jwt_decode(response1.data.data);
                  if (decoded.scope == "ADMIN") {
                    this.$router.replace("/admin");
                  } else if (decoded.scope == "BORROWER") {
                    this.$router.replace("/booking/view");
                  } else if (decoded.scope == "GOP") {
                    this.$router.replace("/gop/booking/view");
                  } else {
                    alert("Incorrect credentials! Please try again.");
                  }
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        })
        .catch((error) => {
          console.log(error);
          this.toastrResponse = {status: "Error", msg: "Incorrect credentials! Please try again."};    
          var bsAlert = new Toast(document.getElementById("theToastr"));
          bsAlert.show();
        });
    },
    user_register() {
      this.userlogin = false;
      this.userregister = true;
      this.userforgotpassword = false;
    },
    user_login() {
      this.userlogin = true;
      this.userregister = false;
      this.userforgotpassword = false;
      this.isResetButtonEmailClicked = false;
      this.isRegisterButtonClicked = false;
    },
    user_forgotpassword() {
      this.userlogin = false;
      this.userforgotpassword = true;
    },
    async reset_password() {
      this.isResetButtonEmailClicked = true;


      try {
        await LoginService.resetUserPassword(this.email);
        this.toastrResponse = { status: "Success", msg: "An email has been successfully sent to you, please click on that link to reset your password!", };


      } catch (e) {
        this.toastrResponse = { status: "Error", msg: "Something went wrong.. We're unable to reset your password", };

      } finally {
        var bsAlert = new Toast(document.getElementById("theToastr"));
        bsAlert.show();

      }


    },
    async register(){
      this.isRegisterButtonClicked = true;
      var that = this;
      try {
        let userDetails = {
          "username": this.username,
          "email": this.email,
          "contactNumber": this.contactNumber,
          "userType": "BORROWER",
          "password": this.password,
          "defunct": false
        };
        await LoginService.registerUser(userDetails);
        this.toastrResponse = {status: "Success", msg: "Registration was successful! Please check your email for verification link"};    
        setTimeout(function(){ that.user_login() }, 1250);
      } catch (e){
        this.isRegisterButtonClicked = false;
        this.toastrResponse = e.response.status == 406 ? {status: "Error", msg: "Duplicated email/username found, please change!"} : {status: "Error", msg: "Something went wrong, unable to register user"};
      } finally{
        var bsAlert = new Toast(document.getElementById("theToastr"));
        bsAlert.show();
      }
    }
  },

};
</script>

<style>
.card-header:first-child {
  border-radius: calc(1rem - 1px) calc(1rem - 1px) 0 0;
}
.card-header {
  position: relative;
  padding: 2rem 2rem;
  border-bottom: none;
  background-color: white;
  box-shadow: 0 0.125rem 0.25rem rgb(0 0 0 / 8%);
  z-index: 2;
}
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: none;
  box-shadow: 0 0.5rem 1rem rgb(0 0 0 / 15%);
  border-radius: 1rem;
}
.bg-gray-100 {
  background-color: #f8f9fa !important;
}
body {
  font-family: "Poppins" !important;
}
.text-primary {
  color: #4650dd !important;
}
h1,
.h1,
h2,
.h2,
h3,
.h3,
h4,
.h4,
h5,
.h5,
h6,
.h6 {
  font-weight: 700;
  line-height: 1.2;
}
.text-muted {
  color: #6c757d !important;
}
.lead {
  font-size: 1.125rem;
  font-weight: 300;
}
.text-sm {
  font-size: 0.7875rem !important;
}
h3,
.h3 {
  font-size: 1.575rem;
}
.page-holder {
  display: flex;
  overflow-x: hidden;
  width: 100%;
  min-height: calc(100vh - 72px);

  flex-wrap: wrap;
}
a {
  color: #4650dd !important;
  text-decoration: underline !important;
  cursor: pointer;
}
</style>