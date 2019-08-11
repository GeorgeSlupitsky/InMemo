export const FETCH_DATA_SUCCESS = 'FETCH_DATA_SUCCESS'
export const FETCH_DATA_ERROR = 'FETCH_DATA_ERROR'
export const REMOVE_ITEM = 'REMOVE_ITEM'
export const CLEAR_DATA = 'CLEAR_DATA'
export const ADD_ITEM_TO_ARRAY = 'ADD_ITEM_TO_ARRAY'

const fetchData = (service, getUrl) => {
    return dispatch => {
        service.fetchData(getUrl)
            .then(response => {
                dispatch({
                    type: FETCH_DATA_SUCCESS,
                    payload: response.data
                })
            })
            .catch((err) => {
                console.log(err)
                dispatch({
                    type: FETCH_DATA_ERROR,
                    payload: 'error'
                })
            })
    }
}

const removeItem = (service, deleteURL, id) => {
    return dispatch => {
        service.deleteItem(deleteURL, id)
            .then(() => {
                dispatch({
                    type: REMOVE_ITEM,
                    payload: id
                })
            })
    }
}

const clearData = () => {
    return {
        type: CLEAR_DATA
    }
}

const addItemToArray = (id) => {
    return {
        type: ADD_ITEM_TO_ARRAY,
        payload: id
    }
}

export {
    fetchData,
    removeItem,
    clearData,
    addItemToArray
}