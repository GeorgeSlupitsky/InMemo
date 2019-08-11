import {
    FETCH_DRUMSTICK_ENUMS_DATA,
    FETCH_DATA_ITEM_SUCCESS,
    FETCH_DATA_ITEM_ERROR,
    CLEAR_DATA,
    SAVE_ITEM_TO_STATE,
    SAVE_PHOTO_TO_STATE,
    POST_PUT_ITEM,
    UPLOAD_PHOTO
} from './actions'

let emptyItem = {
    band: '',
    drummerName: '',
    date: '',
    city: '',
    description: ''
}

const initialState = {
    isLoadingItem: true,
    isLoadingEnums: true,
    item: emptyItem,
    photo: null,
    drumStickEnums: {}
}

export const drumStickEditReducer = (state = initialState, action) => {
    switch (action.type) {
        case FETCH_DRUMSTICK_ENUMS_DATA:
            return {
                ...state,
                isLoadingEnums: false,
                drumStickEnums: action.payload
            }
        case FETCH_DATA_ITEM_SUCCESS:
            return {
                ...state,
                isLoadingItem: false,
                item: action.payload
            }
        case FETCH_DATA_ITEM_ERROR:
            return {
                ...state
            }
        case CLEAR_DATA:
            return {
                ...state,
                isLoadingItem: true,
                isLoadingEnums: true,
                item: emptyItem,
                date: '',
                photo: null,
                drumStickEnums: {}
            }
        case SAVE_ITEM_TO_STATE:
            return {
                ...state,
                item: action.payload
            }
        case SAVE_PHOTO_TO_STATE:
            return {
                ...state,
                photo: action.payload
            }
        case POST_PUT_ITEM:
            return {
                ...state
            }
        case UPLOAD_PHOTO:
            return {
                ...state
            }
        default:
            return state
    }
}

