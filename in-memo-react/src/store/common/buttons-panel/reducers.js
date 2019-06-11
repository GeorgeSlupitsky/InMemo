import {
    TOGGLE_DOWNLOAD_MODAL,
    TOGGLE_UPLOAD_MODAL,
    CLOSE_MODAL,
    UPLOAD_FILE,
    TOGGLE_REWRITE_DB
} from './actions'

const initialState = {
    collapseDownload: false,
    collapseUpload: false,
    file: null,
    rewriteDB: false,
    localizedMessages: {}
}

export const buttonsPanelReducer = (state = initialState, action) => {
    switch (action.type) {
        case TOGGLE_DOWNLOAD_MODAL:
            return {
                ...state,
                collapseDownload: !state.collapseDownload
            }
        case TOGGLE_UPLOAD_MODAL:
            return {
                ...state,
                collapseUpload: !state.collapseUpload
            }
        case CLOSE_MODAL:
            return {
                ...state,
                collapseDownload: false,
                collapseUpload: false
            }
        case UPLOAD_FILE:
            return {
                ...state,
                file: action.payload
            }
        case TOGGLE_REWRITE_DB:
            return {
                ...state,
                rewriteDB: !state.rewriteDB
            }
        default:
            return state
    }
}