import React, { Component } from 'react'
import { Link, withRouter } from 'react-router-dom'
import { Button, Container, FormGroup, Label, Input, Card, CardImg } from 'reactstrap'
import AppNavbar from '../../common/AppNavbar'
import { FormattedMessage } from 'react-intl'
import { AvGroup, AvForm, AvField, AvInput, AvFeedback } from 'availity-reactstrap-validation'

class DrumStickEdit extends Component {

    constructor(props) {
        super(props)
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.handleSave = this.handleSave.bind(this)
        this.formatDate = this.formatDate.bind(this)
        this.onChangeFile = this.onChangeFile.bind(this)
    }

    componentDidMount() {
        const { service, fetchDrumStickEnumsData, fetchDataItem, itemId } = this.props

        if (itemId !== 'new') {
            fetchDataItem(service, '/api/drumstick/', itemId)         
        }

        fetchDrumStickEnumsData(service)
    }

    componentWillUnmount() {
        const { clearData } = this.props
        clearData()
    }

    async handleSave() {
        const { service, itemId, item, photo, postPutItem, uploadPhoto } = this.props
        await postPutItem(service, '/api/drumstick/', item, itemId)
        if (itemId !== undefined) {
            if (photo !== null) {
                await uploadPhoto(service, '/api/drumstick/uploadPhoto', itemId, photo)
            }
        }
        await this.props.history.push('/drumstick')
    }

    handleSubmit(event, errors) {
        if (errors.length === 0) {
            this.handleSave()
        }
    }

    handleChange(event) {
        const { value, name } = event.target
        const { item, saveItemToState } = this.props
        item[name] = value
        saveItemToState(item)
    }

    formatDate(date) {
        let strings = date.split('-')
        return strings[2] + '.' + strings[1] + '.' + strings[0]
    }

    onChangeFile(e) {
        const { files } = e.target
        const { savePhotoToState } = this.props
        savePhotoToState(files[0])
    }

    render() {
        const { item, drumStickEnums, localizedMessages, isLoadingItem, isLoadingEnums, itemId } = this.props

        let date

        if (itemId !== 'new') {
            if (isLoadingItem || isLoadingEnums) {
                return <p>Loading...</p>
            }
            date = this.formatDate(item.date)
        } else {
            if (isLoadingEnums) {
                return <p>Loading...</p>
            }
        }

        const title = <h2>{item.id ? (
            <FormattedMessage id="DrumStickEdit.edit" defaultMessage="Edit Drumstick" />
        ) : (
                <FormattedMessage id="DrumStickEdit.add" defaultMessage="Add Drumstick" />)}</h2>

        let optionCityItems = drumStickEnums.cities.map((city) =>
            <option key={city.id} value={city.drumStickCityEnum}>{city.name}</option>
        )

        let optionDescriptionItems = drumStickEnums.descriptions.map((description) =>
            <option key={description.id} value={description.drumStickTypeEnum}>{description.name}</option>
        )

        let link = "http://localhost:8090" + item.linkToPhoto //for localhost
        // let link = "http://api:8085" + item.linkToPhoto //for Docker

        return <div>
            <AppNavbar />
            <Container>
                {title}
                <AvForm onSubmit={this.handleSubmit}>
                    <AvGroup>
                        <Label for="band">
                            <FormattedMessage id="DrumStickEdit.band" defaultMessage="Band" />
                        </Label>
                        <AvInput type="text" name="band" id="band" value={item.band || ''}
                            onChange={this.handleChange} required />
                        <AvFeedback>
                            <FormattedMessage id="DrumStickEdit.band.error" defaultMessage="Please enter band" />
                        </AvFeedback>
                    </AvGroup>
                    <AvGroup>
                        <Label for="drummerName">
                            <FormattedMessage id="DrumStickEdit.drummerName" defaultMessage="Drummer Name" />
                        </Label>
                        <AvInput type="text" name="drummerName" id="drummerName" value={item.drummerName || ''}
                            onChange={this.handleChange} required />
                        <AvFeedback>
                            <FormattedMessage id="DrumStickEdit.drummerName.error" defaultMessage="Please enter band" />
                        </AvFeedback>
                    </AvGroup>
                    <AvGroup>
                        <Label for="date">
                            <FormattedMessage id="DrumStickEdit.date" defaultMessage="Date" />
                        </Label>
                        <AvField name="date" id="date" type="text" value={date || ''}
                            onChange={this.handleChange} validate={{
                                required: { value: true, errorMessage: localizedMessages.errorMessageDateRequired },
                                date: { format: 'DD.MM.YYYY', errorMessage: localizedMessages.errorMessageDatePattern }
                            }} />
                    </AvGroup>
                    <div className="row">
                        <AvGroup className="col-md-6 mb-3">
                            <AvField type="select" name="city" id="city" value={item.city || ''}
                                label={localizedMessages.selectLabelCity}
                                onChange={this.handleChange} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageCity }
                                }}>
                                <option value="" disabled>--</option>
                                {optionCityItems}
                            </AvField>
                        </AvGroup>
                        <AvGroup className="col-md-6 mb-3">
                            <AvField type="select" name="description" id="description" value={item.description || ''}
                                label={localizedMessages.selectLabelDescription}
                                onChange={this.handleChange} validate={{
                                    required: { value: true, errorMessage: localizedMessages.errorMessageDescription }
                                }}>
                                <option value="" disabled>--</option>
                                {optionDescriptionItems}
                            </AvField>
                        </AvGroup>
                    </div>
                    {item.id !== undefined && item.linkToPhoto === null ? (
                        <div>
                            <Label for="upload">
                                <FormattedMessage id="DrumStickEdit.addPhoto" defaultMessage="Add Photo:" />
                            </Label>
                            <Input type="file" name="upload" onChange={this.onChangeFile} />
                            <br />
                        </div>
                    ) : ("")}
                    {item.id !== undefined && item.linkToPhoto !== null ? (
                        <div>
                            <Label for="upload">
                                <FormattedMessage id="DrumStickEdit.photo" defaultMessage="Photo:" />
                            </Label>
                            <Card style={{ width: '18rem' }}>
                                <CardImg src={link} ></CardImg>
                            </Card>
                            <br />
                        </div>
                    ) : ("")}
                    <FormGroup>
                        <Button color="primary" type="submit">
                            <FormattedMessage id="DrumStickEdit.save" defaultMessage="Save" />
                        </Button>{' '}
                        <Button color="secondary" tag={Link} to='/drumsticks'>
                            <FormattedMessage id="DrumStickEdit.cancel" defaultMessage="Cancel" />
                        </Button>
                    </FormGroup>
                </AvForm>
            </Container>
        </div>
    }
}

export default withRouter(DrumStickEdit)