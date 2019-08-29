import React, { Component } from "react"
import { Button, Container, FormGroup, Input, Label } from 'reactstrap'
import AppNavbar from '../../common/AppNavbar'
import { FormattedMessage } from 'react-intl'
import { AvForm, AvGroup, AvInput, AvFeedback, AvField } from 'availity-reactstrap-validation'
import { Link } from 'react-router-dom'

export default class CDEdit extends Component {

    constructor(props) {
        super(props)
        this.handleSave = this.handleSave.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleCheckbox = this.handleCheckbox.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.handleChangeForBand = this.handleChangeForBand.bind(this)
        this.handleChangeForBandMember = this.handleChangeForBandMember.bind(this)
        this.onEnterBandMember = this.onEnterBandMember.bind(this)
        this.removeBandMemberFromArray = this.removeBandMemberFromArray.bind(this)
    }

    componentDidMount() {
        const { service, fetchCDEnumsData, fetchDataItem, itemId } = this.props
        if (itemId !== 'new') {
            fetchDataItem(service, '/api/cd/', itemId)
        }
        fetchCDEnumsData(service)
    }

    componentWillUnmount() {
        const { clearData } = this.props
        clearData()
    }

    async handleSave() {
        const { url, service, itemId, item, postPutItem, bandMember, saveBandMemberToState } = this.props
        if (bandMember !== "" || bandMember !== undefined){
            item.band.bandMembers.push(bandMember)
        }
        await postPutItem(service, '/api/cd/', item, itemId)
        saveBandMemberToState('')
        await this.props.history.push(url)
    }

    handleSubmit(event, errors) {
        if (errors.length === 0) {
            this.handleSave()
        }
    }

    handleCheckbox(event) {
        const { item, saveItemToState } = this.props
        item.autograph = !item.autograph
        saveItemToState(item)
    }

    handleChange(event) {
        const { value, name } = event.target
        const { item, saveItemToState } = this.props
        item[name] = value
        saveItemToState(item)
    }

    handleChangeForBand(event) {
        const { value, id, name } = event.target
        const { item, saveItemToState } = this.props
        item[id][name] = value
        saveItemToState(item)
    }

    handleChangeForBandMember(event) {
        const { value } = event.target
        const { saveBandMemberToState } = this.props
        saveBandMemberToState(value)
    }

    onEnterBandMember(event) {
        if (event.key === 'Enter') {
            event.preventDefault()
            const { value } = event.target
            const { item, saveItemToState, saveBandMemberToState } = this.props
            let member = { name: value }
            item.band.bandMembers.push(member)
            saveItemToState(item)
            saveBandMemberToState('')
        }
    }

    removeBandMemberFromArray(arr, value) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name === value) {
                arr.splice(i, 1)
                break
            }
        }
        const { item, saveItemToState } = this.props
        item.band.bandMembers = arr
        saveItemToState(item)
        this.forceUpdate()
    }

    render() {

        const { url, item, itemId, cdEnums, localizedMessages, isLoadingItem, isLoadingEnums, bandMember } = this.props

        if (itemId !== 'new'){
            if (isLoadingItem || isLoadingEnums) {
                return <p>Loading...</p>
            }
        } else {
            if (isLoadingEnums) {
                return <p>Loading...</p>
            }
        }

        const title = <h2>{itemId ? (
            <FormattedMessage id="CDEdit.edit" defaultMessage="Edit CD" />
        ) : (
                <FormattedMessage id="CDEdit.add" defaultMessage="Add CD" />)}</h2>

        let optionBookletItems = cdEnums.booklets.map((booklet) =>
            <option key={booklet.id} value={booklet.cdBookletEnum}>{booklet.name}</option>
        )

        let optionCountryItems = cdEnums.countries.map((country) =>
            <option key={country.id} value={country.cdCountryEnum}>{country.name}</option>
        )

        let optionTypeItems = cdEnums.types.map((type) =>
            <option key={type.id} value={type.cdTypeEnum}>{type.name}</option>
        )

        let optionGroupItems = cdEnums.groups.map((group) =>
            <option key={group.id} value={group.cdGroupEnum}>{group.name}</option>
        )

        let optionBandOrderItems = cdEnums.bandOrders.map((bandOrder) =>
            <option key={bandOrder.id} value={bandOrder.cdBandOrder}>{bandOrder.name}</option>
        )

        let bandMembers = item.band.bandMembers.map((bandMember) => {
            return <p key={bandMember.name}>{bandMember.name + " "}
                <Button size="sm" color="danger" onClick={() => this.removeBandMemberFromArray(item.band.bandMembers, bandMember.name)}>X</Button>
            </p>
        })

        return <div>
            <AppNavbar />
            <Container>
                {title}
                <AvForm onSubmit={this.handleSubmit}>
                    <AvGroup>
                        <Label for="name">
                            <FormattedMessage id="CDEdit.band" defaultMessage="Band" />
                        </Label>
                        <AvInput type="text" name="name" id="band" value={item.band.name}
                            onChange={this.handleChangeForBand} required />
                        <AvFeedback>
                            <FormattedMessage id="CDEdit.band.error" defaultMessage="Please enter band" />
                        </AvFeedback>
                    </AvGroup>
                    <AvGroup>
                        <Label for="album">
                            <FormattedMessage id="CDEdit.album" defaultMessage="Album" />
                        </Label>
                        <AvInput type="text" name="album" id="album" value={item.album || ''}
                            onChange={this.handleChange} required />
                        <AvFeedback>
                            <FormattedMessage id="CDEdit.album.error" defaultMessage="Please enter album" />
                        </AvFeedback>
                    </AvGroup>
                    <AvGroup>
                        <Label for="year">
                            <FormattedMessage id="CDEdit.year" defaultMessage="Year" />
                        </Label>
                        <AvField type="text" name="year" id="year" value={item.year || ''}
                            onChange={this.handleChange}
                            validate={{
                                required: { value: true, errorMessage: localizedMessages.errorMessageYearRequired },
                                pattern: { value: '^[-]$|(19|20)[0-9][0-9]$', errorMessage: localizedMessages.errorMessageYearPattern }
                            }} />
                    </AvGroup>
                    <FormGroup>
                        <Label for="bandMambers">
                            <FormattedMessage id="CDEdit.bandMembers" defaultMessage="Band Members" />
                        </Label>
                        <Input type="text" name="bandMambers" id="bandMembers" value={bandMember}
                            onChange={this.handleChangeForBandMember} onKeyPress={this.onEnterBandMember} />
                    </FormGroup>
                    {bandMembers}
                    <AvGroup>
                        <Label for="discogsLink">
                            <FormattedMessage id="CDEdit.discogsLink" defaultMessage="Link to Discogs" />
                        </Label>
                        <AvField type="text" name="discogsLink" id="discogsLink" value={item.discogsLink || ''}
                            onChange={this.handleChange} />
                    </AvGroup>
                    <div className="row">
                        <AvGroup className="col-md-2 mb-3">
                            <AvField type="select" name="booklet" id="booklet" value={item.booklet || ''}
                                label={localizedMessages.selectLabelBooklet}
                                onChange={this.handleChange} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageBooklet }
                                }}>
                                <option value="" disabled>--</option>
                                {optionBookletItems}
                            </AvField>
                        </AvGroup>
                        <AvGroup className="col-md-2 mb-3">
                            <AvField type="select" name="countryEdition" id="countryEdition" value={item.countryEdition || ''}
                                label={localizedMessages.selectLabelCountry}
                                onChange={this.handleChange} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageCountry }
                                }}>
                                <option value="" disabled>--</option>
                                {optionCountryItems}
                            </AvField>
                        </AvGroup>
                        <AvGroup className="col-md-3 mb-3">
                            <AvField type="select" name="cdType" id="cdType" value={item.cdType || ''}
                                label={localizedMessages.selectLabelType}
                                onChange={this.handleChange} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageType }
                                }}>
                                <option value="" disabled>--</option>
                                {optionTypeItems}
                            </AvField>
                        </AvGroup>
                        <AvGroup className="col-md-3 mb-3">
                            <AvField type="select" name="cdGroup" id="cdGroup" value={item.cdGroup || ''}
                                label={localizedMessages.selectLabelGroup}
                                onChange={this.handleChange} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageGroup }
                                }}>
                                <option value="" disabled>--</option>
                                {optionGroupItems}
                            </AvField>
                        </AvGroup>
                        <AvGroup className="col-md-2 mb-3">
                            <AvField type="select" name="order" id="band" value={item.band.order || ''}
                                label={localizedMessages.selectLabelOrder}
                                onChange={this.handleChangeForBand} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageOrder }
                                }}>
                                <option value="" disabled>--</option>
                                {optionBandOrderItems}
                            </AvField>
                        </AvGroup>
                    </div>
                    <AvGroup name="checkbox">
                        <AvInput type="checkbox" name="checkbox" value={item.autograph} onClick={this.handleCheckbox}/>
                        <Label for="checkbox">
                            <FormattedMessage id="CDEdit.autograph" defaultMessage="Autograph" />
                        </Label>
                    </AvGroup>
                    <FormGroup>
                        <Button color="primary">
                            <FormattedMessage id="CDEdit.save" defaultMessage="Save" />
                        </Button>{' '}
                        <Button color="secondary" tag={Link} href={url} to={url}>
                            <FormattedMessage id="CDEdit.cancel" defaultMessage="Cancel" />
                        </Button>
                    </FormGroup>
                </AvForm>
            </Container>
        </div>
    }

}