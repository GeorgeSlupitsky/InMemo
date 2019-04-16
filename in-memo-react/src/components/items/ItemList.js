import React, { Component } from 'react'
import { Container, Table } from 'reactstrap'
import AppNavbar from '../../common/AppNavbar'
import DrumStickItem from './DrumStickItem'
import ButtonsPanel from '../../common/ButtonsPanel'
import CDItem from './CDItem'
import { FormattedMessage } from 'react-intl'

class ItemList extends Component {

    constructor(props) {
        super(props)
        this.state = {
            data: [],
            isLoading: true,
            checkedIds: []
        }
        this.removeItem = this.removeItem.bind(this)
        this.refreshPage = this.refreshPage.bind(this)
        this.addCheckedItemToArray = this.addCheckedItemToArray.bind(this)
        this.removeCheckedItemFromArray = this.removeCheckedItemFromArray.bind(this)
    }

    componentDidMount() {
        this.setState({ isLoading: true })
        const { getURL } = this.props
        fetch(getURL)
            .then(response => response.json())
            .then(data => this.setState({ data: data, isLoading: false }))
    }

    refreshPage() {
        this.setState({ isLoading: true })
        const { getURL } = this.props
        fetch(getURL)
            .then(response => response.json())
            .then(data => this.setState({ data: data, isLoading: false }))
    }

    async removeItem(id) {
        const { deleteURL } = this.props
        await fetch(deleteURL + `${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedData = [...this.state.data].filter(i => i.id !== id)
            this.setState({ data: updatedData })
        })
    }

    removeCheckedItemFromArray(arr, value) {
        return arr.filter(function (ele) {
            return ele !== value
        })
    }

    addCheckedItemToArray(id) {
        let checkedIds = this.state.checkedIds
        this.setState(prevState => {
            const updatedItem = prevState.data.map(item => {
                if (item.id === id) {
                    if (!item.checked) {
                        checkedIds.push(item.id)
                        this.setState({ checkedIds: checkedIds })
                    } else {
                        let filtered = this.removeCheckedItemFromArray(checkedIds, item.id)
                        this.setState({ checkedIds: filtered })
                    }
                    item.checked = !item.checked
                }
                return item
            })
            return {
                data: updatedItem
            }
        })
    }

    render() {
        const { data, isLoading } = this.state
        const { group } = this.props
        if (isLoading) {
            return <p>Loading...</p>
        }

        let number = 1

        const list = data.map(item => {
            if (group === 'all') {
                return <CDItem key={item.id} number={number++} cd={item} removeItem={this.removeItem} editLink='/cds/' />
            } else if (group === 'foreign') {
                return <CDItem key={item.id} number={number++} cd={item} removeItem={this.removeItem} editLink='/cdsForeign/' />
            } else if (group === 'domestic') {
                return <CDItem key={item.id} number={number++} cd={item} removeItem={this.removeItem} editLink='/cdsDomestic/' />
            } else {
                return <DrumStickItem key={item.id} number={number++} drumstick={item} addCheckedItemToArray={this.addCheckedItemToArray} removeItem={this.removeItem} />
            }
        })

        let buttonsPanel, collection
        if (group === 'all') {
            buttonsPanel = <ButtonsPanel collection='cds' refreshPage={this.refreshPage} addLink='/cds/new' />
            collection = <FormattedMessage id="ItemList.collectionCD" defaultMessage="My CD Collection"/>
        } else if (group === 'foreign') {
            buttonsPanel = <ButtonsPanel collection='cds' refreshPage={this.refreshPage} addLink='/cdsForeign/new' />
            collection = <FormattedMessage id="ItemList.collectionCDForeign" defaultMessage="My Foreign CD Collection"/>
        } else if (group === 'domestic') {
            buttonsPanel = <ButtonsPanel collection='cds' refreshPage={this.refreshPage} addLink='/cdsDomestic/new' />
            collection = <FormattedMessage id="ItemList.collectionCDDomestic" defaultMessage="My Domestic CD Collection"/>
        } else {
            buttonsPanel = <ButtonsPanel collection="drumsticks" refreshPage={this.refreshPage} drumstickCheckedIds={this.state.checkedIds} addLink='/drumsticks/new' />
            collection = <FormattedMessage id="ItemList.collectionDrumstick" defaultMessage="My Drumstick Collection"/>
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
                                    <th width="15%"><FormattedMessage id="ItemList.band" defaultMessage="Band"/></th>
                                    <th width="15%"><FormattedMessage id="ItemList.album" defaultMessage="Album"/></th>
                                    <th width="10%"><FormattedMessage id="ItemList.year" defaultMessage="Year"/></th>
                                    <th width="10%"><FormattedMessage id="ItemList.booklet" defaultMessage="Booklet"/></th>
                                    <th width="10%"><FormattedMessage id="ItemList.country" defaultMessage="Country"/></th>
                                    <th width="10%"><FormattedMessage id="ItemList.type" defaultMessage="Type"/></th>
                                    <th width="20%"><FormattedMessage id="ItemList.members" defaultMessage="Members"/></th>
                                    <th width="5%"><FormattedMessage id="ItemList.actions" defaultMessage="Actions"/></th>
                                </tr>
                            ) : (
                                    <tr>
                                        <th width="2%"></th>
                                        <th width="2%">№</th>
                                        <th width="20%"><FormattedMessage id="ItemList.band" defaultMessage="Band"/></th>
                                        <th width="26%"><FormattedMessage id="ItemList.drummerName" defaultMessage="Drummer Name"/></th>
                                        <th width="15%"><FormattedMessage id="ItemList.date" defaultMessage="Date"/></th>
                                        <th width="15%"><FormattedMessage id="ItemList.city" defaultMessage="City"/></th>
                                        <th width="15%"><FormattedMessage id="ItemList.description" defaultMessage="Description"/></th>
                                        <th width="5%"><FormattedMessage id="ItemList.actions" defaultMessage="Actions"/></th>
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

export default ItemList