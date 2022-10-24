<template>
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="text-center">
                <h1 class="display-1 fw-bold">404</h1>
                <p class="fs-3"> <span class="text-danger">Oops!</span> Page not found.</p>
                <p class="lead">
                    The page you’re looking for doesn’t exist.
                  </p>
                <a href="#" class="btn btn-primary">
                    <router-link to="/login" class="text-decoration-none">
                        <span class="d-none d-sm-inline text-light">To Login</span>
                    </router-link>
                </a>
            </div>
        </div>
</template>

<script>
export default {

}
</script>

<style>

</style>