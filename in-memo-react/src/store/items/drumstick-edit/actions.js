export const FETCH_DRUMSTICK_ENUMS_DATA = 'FETCH_DRUMSTICK_ENUMS_DATA'
export const FETCH_DATA_ITEM_SUCCESS = 'FETCH_DATA_ITEM_SUCCESS'
export const FETCH_DATA_ITEM_ERROR = 'FETCH_DATA_ITEM_ERROR'
export const CLEAR_DATA = 'CLEAR_DATA'
export const SAVE_ITEM_TO_STATE = 'SAVE_ITEM_TO_STATE'
export const SAVE_PHOTO_TO_STATE = 'SAVE_PHOTO_TO_STATE'
export const POST_PUT_ITEM = 'POST_PUT_ITEM'
export const UPLOAD_PHOTO = 'UPLOAD_PHOTO'

const fetchDrumStickEnumsData = (service) => {
    return async dispatch => {
        let enumsData = {}
        await service.fetchEnumData('/api/enums/drumsticks/cities/')
            .then(response => {
            enumsData.cities = response.data
        })
        await service.fetchEnumData('/api/enums/drumstick/types/')
            .then(response => {
            enumsData.descriptions = response.data
        })
        await dispatch({
            type: FETCH_DRUMSTICK_ENUMS_DATA,
            payload: enumsData
        })
    }
}

const fetchDataItem = (service, url, id) => {
    return dispatch => {
        service.fetchDataItem(url, id)
            .then(response => {
                dispatch({
                    type: FETCH_DATA_ITEM_SUCCESS,
                    payload: response.data
                })
            })
            .catch((err) => {
                console.log(err)
                dispatch({
                    type: FETCH_DATA_ITEM_ERROR,
                    payload: 'error'
                })
            })
    }
}

const clearData = () => {
    return {
        type: CLEAR_DATA
    }
}

const saveItemToState = (item) => {
    return {
        type: SAVE_ITEM_TO_STATE,
        payload: item
    }
}

const savePhotoToState = (photo) => {
    return {
        type: SAVE_PHOTO_TO_STATE,
        payload: photo
    }
}

const postPutItem = (service, url, item, id) => {
    return dispatch => {
        service.postPutItem(url, id, item)
            .then(response => {
                dispatch({
                    type: POST_PUT_ITEM,
                    payload: response.data
                })
            })
    }
}

const uploadPhoto = (service, url, itemId, photo) => {
    return dispatch => {
        service.uploadPhoto(photo, itemId, url)
            .then(response => {
                dispatch({
                    type: UPLOAD_PHOTO,
                    payload: response.data
                })
            })
    }
}

export {
    fetchDrumStickEnumsData,
    fetchDataItem,
    clearData,
    saveItemToState,
    savePhotoToState,
    postPutItem,
    uploadPhoto
}

