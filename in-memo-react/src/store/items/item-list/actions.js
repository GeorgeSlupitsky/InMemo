export const FETCH_DATA = 'FETCH_DATA'
export const CLEAR_DATA = 'CLEAR_DATA'
export const REMOVE_ITEM = 'REMOVE_ITEM'
export const ADD_ITEM_TO_ARRAY = 'ADD_ITEM_TO_ARRAY'

const fetchData = (service, getUrl) => {
    return dispatch => {
        service.fetchData(getUrl)
            .then(response => {
                dispatch({
                    type: FETCH_DATA,
                    payload: response.data
                })
            })
    }
}

const clearData = () => {
    return {
        type: CLEAR_DATA
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

const addItemToArray = (id) => {
    return {
        type: ADD_ITEM_TO_ARRAY,
        payload: id
    }
}

export {
    fetchData,
    clearData,
    removeItem,
    addItemToArray
}