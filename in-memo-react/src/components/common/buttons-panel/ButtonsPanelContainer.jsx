import React, { Component } from 'react'
import ButtonsPanel from './ButtonsPanel'
import { connect } from 'react-redux'
import compose from './../../../utils/compose'
import withService from './../../../hoc/withService'
import {
    toggleDownloadModal,
    toggleUploadModal,
    closeModal,
    uploadFile,
    toggleRewriteDB
} from '../../../store/common/buttons-panel/actions'

class ButtonsPanelContainer extends Component {
    render() {
        const { service,
            collection,
            addLink,
            drumstickCheckedIds,
            collapseDownload,
            collapseUpload,
            file,
            rewriteDB,
            toggleDownloadModal,
            toggleUploadModal,
            toggleRewriteDB,
            uploadFile,
            localizedMessages
        } = this.props

        return <ButtonsPanel
            service={service}
            collection={collection}
            addLink={addLink}
            drumstickCheckedIds={drumstickCheckedIds}
            collapseDownload={collapseDownload}
            collapseUpload={collapseUpload}
            file={file}
            rewriteDB={rewriteDB}
            toggleDownloadModal={toggleDownloadModal}
            toggleUploadModal={toggleUploadModal}
            toggleRewriteDB={toggleRewriteDB}
            uploadFile={uploadFile}
            localizedMessages={localizedMessages}
        />
    }
}

const mapStateToProps = state => {
    return {
        collapseDownload: state.buttonsPanel.collapseDownload,
        collapseUpload: state.buttonsPanel.collapseUpload,
        file: state.buttonsPanel.file,
        rewriteDB: state.buttonsPanel.rewriteDB
    }
}

const mapDispatchToProps = {
    toggleDownloadModal,
    toggleUploadModal,
    closeModal,
    uploadFile,
    toggleRewriteDB
}

export default compose(
    withService(),
    connect(mapStateToProps, mapDispatchToProps)
)(ButtonsPanelContainer)