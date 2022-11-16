class BaseApiService {
    handleError(err) {
        console.log(err.response)

        throw err;
    }
}

export default BaseApiService;