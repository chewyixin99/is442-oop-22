<template>
    <div class="page-holder align-items-center py-4 bg-gray-100 vh-100">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6 px-lg-4">
                    <div class="card">
                        <div class="card-header px-lg-5">
                            <div class="card-heading text-dark"><h2>Reset Password</h2></div>
                        </div>
                        <div class="card-body p-lg-5">
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
                        <button
                            v-if="!isResetPasswordButtonClicked"
                            class="btn btn-primary"
                            id="reset"
                            type="button"
                            name="resetSubmit"
                            :disabled=disabled
                            @click="reset_password()"
                        >
                            Reset
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
                    </div>
                </div>
                <div class="col-lg-6 col-xl-5 ms-xl-auto px-lg-4 text-center text-primary">
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
        </div>
        <TheToastr :toastrResponse="toastrResponse"></TheToastr>
    </div>
</template>
<script>
import { Toast } from "bootstrap";
import TheToastr from "@/components/TheToastr.vue";
import LoginService from "@/api/services/LoginService";

export default {
  name: "ResetPassword",
  components: {
    TheToastr,
  },
  data() {
    return {
      password: "123",
      toastrResponse: "",
      disabled: false,
      isResetPasswordButtonClicked: false,
    };
  },
  methods: {
    async reset_password(){
        var that = this;
        this.disabled = true;
        let userDetails = {
            "email": this.$route.query.email,
            "newPassword": this.password
        }

        try {
            await LoginService.savePassword(this.$route.query.token, userDetails);
            this.toastrResponse = {status: "Success", msg: "You have successfully resetted your password!"};    
            this.isResetPasswordButtonClicked = true;
            setTimeout(function(){ that.$router.push("/login"); }, 2000);

        } catch (error){
            this.toastrResponse = {status: "Error", msg: "Something went wrong while resetting your password"};    
        } finally {
            var bsAlert = new Toast(document.getElementById("theToastr"));
            bsAlert.show();
        }
    }
  }
}

</script>

