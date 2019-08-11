import React, { Component } from 'react'
import { connect } from "react-redux"
import compose from './../../../utils/compose'
import withService from './../../../hoc/withService'
import CDEdit from './CDEdit'
import {
    fetchCDEnumsData,
    fetchDataItem,
    clearData,
    saveItemToState,
    saveBandMemberToState,
    postPutItem
} from '../../../store/items/cd-edit/actions'

class CDEditContainer extends Component{

    render(){
        const { service,
            match,
            history,
            itemId,
            fetchCDEnumsData,
            fetchDataItem,
            clearData,
            saveItemToState,
            saveBandMemberToState,
            postPutItem,
            localizedMessages,
            item,
            bandMember,
            cdEnums,
            isLoadingItem,
            isLoadingEnums
        } = this.props

        return <CDEdit
            service={service}
            match={match}
            history={history}
            itemId={itemId}
            fetchCDEnumsData={fetchCDEnumsData}
            fetchDataItem={fetchDataItem}
            clearData={clearData}
            saveItemToState={saveItemToState}
            saveBandMemberToState={saveBandMemberToState}
            postPutItem={postPutItem}
            localizedMessages={localizedMessages}
            item={item}
            bandMember={bandMember}
            cdEnums={cdEnums}
            isLoadingItem={isLoadingItem}
            isLoadingEnums={isLoadingEnums}
        />
    }

}

const mapStateToProps = state => {
    return {
        bandMember: state.cdEdit.bandMember,
        item: state.cdEdit.item,
        cdEnums: state.cdEdit.cdEnums,
        isLoadingItem: state.cdEdit.isLoadingItem,
        isLoadingEnums: state.cdEdit.isLoadingEnums
    }
}

const mapDispatchToProps = {
    fetchCDEnumsData,
    fetchDataItem,
    clearData,
    saveItemToState,
    saveBandMemberToState,
    postPutItem
}

export default compose(
    withService(),
    connect(mapStateToProps, mapDispatchToProps)
)(CDEditContainer)