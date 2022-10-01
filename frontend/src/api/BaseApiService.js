class BaseApiService {
    handleError(err) {
        console.log(err.response)
        // if (err.response.status == 403) {
        //     window.location.href = window.location.origin + "/forbidden";
        // } else if (err.response.status == 401) {
        //     if (window.$cookies.isKey("user")) {
        //         window.$cookies.remove("user")
        //     }
        //     window.location.href = window.location.origin + "/login?redirect=true";
        // }
        throw err;
    }
}

export default BaseApiService;