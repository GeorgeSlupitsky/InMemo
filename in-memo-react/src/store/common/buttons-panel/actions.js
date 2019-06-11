export const TOGGLE_DOWNLOAD_MODAL = 'TOGGLE_DOWNLOAD_MODAL'
export const TOGGLE_UPLOAD_MODAL = 'TOGGLE_UPLOAD_MODAL'
export const CLOSE_MODAL = 'CLOSE_MODAL'
export const UPLOAD_FILE = 'UPLOAD_FILE'
export const TOGGLE_REWRITE_DB = 'TOGGLE_REWRITE_DB'

const toggleDownloadModal = () => {
    return {
        type: TOGGLE_DOWNLOAD_MODAL
    }
}

const toggleUploadModal = () => {
    return {
        type: TOGGLE_UPLOAD_MODAL
    }
}

const closeModal = () => {
    return {
        type: CLOSE_MODAL
    }
}

const uploadFile = (file) => {
    return {
        type: UPLOAD_FILE,
        payload: file
    }
}

const toggleRewriteDB = () => {
    return {
        type: TOGGLE_REWRITE_DB
    }
}

export {
    toggleDownloadModal,
    toggleUploadModal,
    closeModal,
    uploadFile,
    toggleRewriteDB
}