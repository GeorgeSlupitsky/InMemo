import React, { Component } from 'react'
import { Container, Table } from 'reactstrap'
import AppNavbar from '../../common/AppNavbar'
import DrumStickItem from '../DrumStickItem'
import CDItem from '../CDItem'
import { FormattedMessage } from 'react-intl'
import ButtonsPanelContainer from '../../common/buttons-panel/ButtonsPanelContainer'

export default class ItemList extends Component {

    constructor(props) {
        super(props)
    }

    componentDidMount() {
        const { service, getURL, fetchData } = this.props
        fetchData(service, getURL)
    }

    componentWillUnmount() {
        const { clearData } = this.props
        clearData()
    }

    render() {
        const { group, data, isLoading, checkedIds, removeItem, addItemToArray, deleteURL, service, localizedMessages } = this.props
        if (isLoading) {
            return <p>Loading...</p>
        }

        let number = 1

        const list = data.map(item => {
            if (group === 'all') {
                return <CDItem
                    key={item.id}
                    number={number++}
                    cd={item}
                    removeItem={removeItem}
                    deleteURL={deleteURL}
                    service={service}
                    editLink='/cds/' />
            } else if (group === 'foreign') {
                return <CDItem
                    key={item.id}
                    number={number++}
                    cd={item}
                    removeItem={removeItem}
                    deleteURL={deleteURL}
                    service={service}
                    editLink='/cdsForeign/' />
            } else if (group === 'domestic') {
                return <CDItem
                    key={item.id}
                    number={number++}
                    cd={item}
                    removeItem={removeItem}
                    deleteURL={deleteURL}
                    service={service}
                    editLink='/cdsDomestic/' />
            } else {
                return <DrumStickItem
                    key={item.id}
                    number={number++}
                    drumstick={item}
                    removeItem={removeItem}
                    deleteURL={deleteURL}
                    service={service}
                    addCheckedItemToArray={addItemToArray} />
            }
        })

        let buttonsPanel, collection

        if (group === 'all') {
            buttonsPanel = <ButtonsPanelContainer collection='cds' addLink='/cds/new' localizedMessages={localizedMessages} />
            collection = <FormattedMessage id="ItemList.collectionCD" defaultMessage="My CD Collection" />
        } else if (group === 'foreign') {
            buttonsPanel = <ButtonsPanelContainer collection='cds' addLink='/cdsForeign/new' localizedMessages={localizedMessages} />
            collection = <FormattedMessage id="ItemList.collectionCDForeign" defaultMessage="My Foreign CD Collection" />
        } else if (group === 'domestic') {
            buttonsPanel = <ButtonsPanelContainer collection='cds' addLink='/cdsDomestic/new' localizedMessages={localizedMessages} />
            collection = <FormattedMessage id="ItemList.collectionCDDomestic" defaultMessage="My Domestic CD Collection" />
        } else {
            buttonsPanel = <ButtonsPanelContainer collection='drumsticks' drumstickCheckedIds={checkedIds} addLink='/drumsticks/new' localizedMessages={localizedMessages} />
            collection = <FormattedMessage id="ItemList.collectionDrumstick" defaultMessage="My Drumstick Collection" />
        }

        return (
            <div>
                <AppNavbar />
                <Container fluid>
                    <h3>{collection}</h3>
                    {buttonsPanel}
                    <br />
                    <br />
                    <Table striped bordered hover className="mt-4">
                        <thead>
                            {group === 'all' || group === 'foreign' || group === 'domestic' ? (
                                <tr>
                                    <th width="2%">№</th>
                                    <th width="15%"><FormattedMessage id="ItemList.band" defaultMessage="Band" /></th>
                                    <th width="15%"><FormattedMessage id="ItemList.album" defaultMessage="Album" /></th>
                                    <th width="10%"><FormattedMessage id="ItemList.year" defaultMessage="Year" /></th>
                                    <th width="10%"><FormattedMessage id="ItemList.booklet" defaultMessage="Booklet" /></th>
                                    <th width="10%"><FormattedMessage id="ItemList.country" defaultMessage="Country" /></th>
                                    <th width="10%"><FormattedMessage id="ItemList.type" defaultMessage="Type" /></th>
                                    <th width="20%"><FormattedMessage id="ItemList.members" defaultMessage="Members" /></th>
                                    <th width="5%"><FormattedMessage id="ItemList.actions" defaultMessage="Actions" /></th>
                                </tr>
                            ) : (
                                    <tr>
                                        <th width="2%"></th>
                                        <th width="2%">№</th>
                                        <th width="20%"><FormattedMessage id="ItemList.band" defaultMessage="Band" /></th>
                                        <th width="26%"><FormattedMessage id="ItemList.drummerName" defaultMessage="Drummer Name" /></th>
                                        <th width="15%"><FormattedMessage id="ItemList.date" defaultMessage="Date" /></th>
                                        <th width="15%"><FormattedMessage id="ItemList.city" defaultMessage="City" /></th>
                                        <th width="15%"><FormattedMessage id="ItemList.description" defaultMessage="Description" /></th>
                                        <th width="5%"><FormattedMessage id="ItemList.actions" defaultMessage="Actions" /></th>
                                    </tr>
                                )}
                        </thead>
                        <tbody>
                            {list}
                        </tbody>
                    </Table>
                </Container>
            </div>
        )
    }
}