import React, { Component } from 'react'
import { connect } from "react-redux"
import compose from './../../../utils/compose'
import withService from './../../../hoc/withService'
import ItemList from './ItemList'
import {
    fetchData,
    removeItem,
    clearData,
    addItemToArray
} from '../../../store/items/item-list/actions'

class ItemListContainer extends Component {

    render() {
        const { service,
            getURL,
            deleteURL,
            group,
            data,
            isLoading,
            checkedIds,
            fetchData,
            removeItem,
            clearData,
            addItemToArray,
            localizedMessages
        } = this.props

        return <ItemList
            service={service}
            getURL={getURL}
            deleteURL={deleteURL}
            group={group}
            data={data}
            isLoading={isLoading}
            checkedIds={checkedIds}
            fetchData={fetchData}
            removeItem={removeItem}
            clearData={clearData}
            addItemToArray={addItemToArray}
            localizedMessages={localizedMessages}
        />
    }
}

const mapStateToProps = state => {
    return {
        data: state.itemList.data,
        isLoading: state.itemList.isLoading,
        checkedIds: state.itemList.checkedIds,
    }
}

const mapDispatchToProps = {
    fetchData,
    removeItem,
    clearData,
    addItemToArray
}

export default compose(
    withService(),
    connect(mapStateToProps, mapDispatchToProps)
)(ItemListContainer)