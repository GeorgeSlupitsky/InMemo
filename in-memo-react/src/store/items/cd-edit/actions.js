export const FETCH_CD_ENUMS_DATA = 'FETCH_ENUMS_DATA'
export const FETCH_DATA_ITEM_SUCCESS = 'FETCH_DATA_ITEM_SUCCESS'
export const FETCH_DATA_ITEM_ERROR = 'FETCH_DATA_ITEM_ERROR'
export const CLEAR_DATA = 'CLEAR_DATA'
export const SAVE_ITEM_TO_STATE = 'SAVE_ITEM_TO_STATE'
export const SAVE_BAND_MEMBER_TO_STATE = 'SAVE_BAND_MEMBER_TO_STATE'
export const POST_PUT_ITEM = 'POST_PUT_ITEM'

const fetchCDEnumsData = (service) => {
    return async dispatch => {
        let enumsData = {}
        await service.fetchEnumData('/api/enums/cds/booklets/')
            .then(response => {
            enumsData.booklets = response.data
        })
        await service.fetchEnumData('/api/enums/cds/countries/')
            .then(response => {
            enumsData.countries = response.data
        })
        await service.fetchEnumData('/api/enums/cds/types/')
            .then(response => {
            enumsData.types = response.data
        })
        await service.fetchEnumData('/api/enums/cds/groups/')
            .then(response => {
            enumsData.groups = response.data
        })
        await service.fetchEnumData('/api/enums/cds/band/orders')
            .then(response => {
            enumsData.bandOrders = response.data
        })
        await dispatch({
            type: FETCH_CD_ENUMS_DATA,
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

const saveBandMemberToState = (bandMember) => {
    return {
        type: SAVE_BAND_MEMBER_TO_STATE,
        payload: bandMember
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

export {
    fetchCDEnumsData,
    fetchDataItem,
    clearData,
    saveItemToState,
    saveBandMemberToState,
    postPutItem
}

