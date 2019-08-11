import React, { Component } from 'react'
import { connect } from "react-redux"
import compose from './../../../utils/compose'
import withService from './../../../hoc/withService'
import DrumStickEdit from './DrumStickEdit'
import {
    fetchDrumStickEnumsData,
    fetchDataItem,
    clearData,
    saveItemToState,
    savePhotoToState,
    postPutItem,
    uploadPhoto
} from '../../../store/items/drumstick-edit/actions'

class DrumstickEditContainer extends Component {

    render() {
        const { service,
            match,
            history,
            itemId,
            fetchDrumStickEnumsData,
            fetchDataItem,
            clearData,
            saveItemToState,
            savePhotoToState,
            postPutItem,
            uploadPhoto,
            localizedMessages,
            item,
            drumStickEnums,
            photo,
            isLoadingItem,
            isLoadingEnums
        } = this.props

        return <DrumStickEdit
            service={service}
            match={match}
            history={history}
            itemId={itemId}
            fetchDrumStickEnumsData={fetchDrumStickEnumsData}
            fetchDataItem={fetchDataItem}
            clearData={clearData}
            saveItemToState={saveItemToState}
            savePhotoToState={savePhotoToState}
            postPutItem={postPutItem}
            uploadPhoto={uploadPhoto}
            localizedMessages={localizedMessages}
            item={item}
            drumStickEnums={drumStickEnums}
            photo={photo}
            isLoadingItem={isLoadingItem}
            isLoadingEnums={isLoadingEnums}
        />
    }

}

const mapStateToProps = state => {
    return {
        item: state.drumStickEdit.item,
        photo: state.drumStickEdit.photo,
        drumStickEnums: state.drumStickEdit.drumStickEnums,
        isLoadingItem: state.drumStickEdit.isLoadingItem,
        isLoadingEnums: state.drumStickEdit.isLoadingEnums
    }
}

const mapDispatchToProps = {
    fetchDrumStickEnumsData,
    fetchDataItem,
    clearData,
    saveItemToState,
    savePhotoToState,
    postPutItem,
    uploadPhoto
}

export default compose(
    withService(),
    connect(mapStateToProps, mapDispatchToProps)
)(DrumstickEditContainer)