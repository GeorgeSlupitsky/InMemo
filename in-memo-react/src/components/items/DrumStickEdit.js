import React, { Component } from 'react'
import { Link, withRouter } from 'react-router-dom'
import { Button, Container, FormGroup, Label, Input, Card, CardImg } from 'reactstrap'
import AppNavbar from '../common/AppNavbar'
import { FormattedMessage } from 'react-intl'
import { AvGroup, AvForm, AvField, AvInput, AvFeedback } from 'availity-reactstrap-validation'
import LocalizedStrings from 'localized-strings'

class DrumStickEdit extends Component {

  emptyItem = {
    band: '',
    drummerName: '',
    date: '',
    city: '',
    description: ''
  }

  constructor(props) {
    super(props)
    this.state = {
      item: this.emptyItem,
      cities: [],
      descriptions: [],
      messages: {},
      date: '',
      photo: null
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.formatDate = this.formatDate.bind(this)
    this.onChangeFile = this.onChangeFile.bind(this)
  }

  async componentDidMount() {
    let messages = new LocalizedStrings({
      en: {
        errorMessageDateRequired: 'Please enter date of the concert',
        errorMessageDatePattern: 'Date must be in format "DD.MM.YYYY"',
        errorMessageCity: 'Please choose city',
        errorMessageDescription: 'Please choose note',
        selectLabelCity: 'City',
        selectLabelDescription: 'Note'
      },
      es: {
        errorMessageDateRequired: 'Por favor ingrese la fecha del concierto',
        errorMessageDatePattern: 'La fecha debe estar en formato "DD.MM.YYYY"',
        errorMessageCity: 'Por favor, elige la ciudad',
        errorMessageDescription: 'Por favor, elija una nota',
        selectLabelCity: 'Ciudad',
        selectLabelDescription: 'Nota'
      },
      ru: {
        errorMessageDateRequired: 'Пожалуйста, укажите дату концерта',
        errorMessageDatePattern: 'Дата должна быть в формате "ДД.ММ.ГГГГ"',
        errorMessageCity: 'Пожалуйста, укажите город',
        errorMessageDescription: 'Пожалуйста, укажите примечание',
        selectLabelCity: 'Город',
        selectLabelDescription: 'Примечание'
      },
      uk: {
        errorMessageDateRequired: 'Будь ласка, вкажіть дату концерту',
        errorMessageDatePattern: 'Дата має бути в форматі "ДД.ММ.РРРР"',
        errorMessageCity: 'Будь ласка, вкажіть місто',
        errorMessageDescription: 'Будь ласка, вкажіть примітку',
        selectLabelCity: 'Місто',
        selectLabelDescription: 'Примітка'
      },
      ja: {
        errorMessageDateRequired: 'コンサートの日を入力してください',
        errorMessageDatePattern: '日付は「DD.MM.YYYY」の形式でなければなりません',
        errorMessageCity: '市を選択してください',
        errorMessageDescription: '注意を選択してください',
        selectLabelCity: '市',
        selectLabelDescription: 'メモ'
      }
    })

    this.setState({messages: messages})

    if (this.props.match.params.id !== 'new') {
      const drumstick = await (await fetch(`/api/drumstick/${this.props.match.params.id}`)).json()
      let date = this.formatDate(drumstick.date)
      this.setState({ item: drumstick, date: date})
    }
    const cities = await (await fetch(`/api/enums/drumsticks/cities/`)).json()
    const descriptions = await (await fetch(`/api/enums/drumstick/types/`)).json()
    this.setState({
      cities: cities,
      descriptions: descriptions
    })
  }

  handleChange(event) {
    const target = event.target
    const value = target.value
    const name = target.name
    let item = { ...this.state.item }
    item[name] = value
    this.setState({ item })
  }

  async handleSubmit(event, errors) {
    if (errors.length === 0) {
      event.preventDefault()
      const { item, photo } = this.state
      await fetch('/api/drumstick', {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
      })
      if (photo !== null){
        const formData = new FormData()
        formData.append('photo', photo)
        formData.append('drumstickId', item.id)
        await fetch(`/api/drumstick/uploadPhoto`, {
          method: 'POST',
          body: formData
        })
      }
      this.props.history.push('/drumsticks')
    }
  }

  formatDate(date) {
    let strings = date.split('-')
    return strings[2] + '.' + strings[1] + '.' + strings[0]
  }

  onChangeFile(e) {
    const { files } = e.target
    this.setState({photo: files[0]})
  } 

  render() {
    const { item, cities, descriptions, date, messages } = this.state
    const title = <h2>{item.id ? (
      <FormattedMessage id="DrumStickEdit.edit" defaultMessage="Edit Drumstick" />
    ) : (
        <FormattedMessage id="DrumStickEdit.add" defaultMessage="Add Drumstick" />)}</h2>

    let optionCityItems = cities.map((city) =>
      <option key={city.id} value={city.drumStickCityEnum}>{city.name}</option>
    )

    let optionDescriptionItems = descriptions.map((description) =>
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
                required: { value: true, errorMessage: messages.errorMessageDateRequired },
                date: { format: 'DD.MM.YYYY', errorMessage: messages.errorMessageDatePattern }
              }} />
          </AvGroup>
          <div className="row">
            <AvGroup className="col-md-6 mb-3">
              <AvField type="select" name="city" id="city" value={item.city || ''}
                label={messages.selectLabelCity}
                onChange={this.handleChange} validate={{
                  required: { value: true, errorMessage: messages.errorMessageCity }
                }}>
                <option value="" disabled>--</option>
                {optionCityItems}
              </AvField>
            </AvGroup>
            <AvGroup className="col-md-6 mb-3">
              <AvField type="select" name="description" id="description" value={item.description || ''}
                label={messages.selectLabelDescription}
                onChange={this.handleChange} validate={{
                  required: { value: true, errorMessage: messages.errorMessageDescription }
                }}>
                <option value="" disabled>--</option>
                {optionDescriptionItems}
              </AvField>
            </AvGroup>
          </div>
          <Label for="upload">
            <FormattedMessage id="DrumStickEdit.addPhoto" defaultMessage="Add Photo:" />
          </Label>
          <Input type="file" name="upload" onChange={this.onChangeFile} />
          <br/>
          {item.linkToPhoto !== null ? (
            <Label for="upload"><FormattedMessage id="DrumStickEdit.photo" defaultMessage="Photo:" /></Label>
          ) : ("")}
          {item.linkToPhoto !== null ? (
            <Card style={{ width: '18rem' }}><CardImg src={link} ></CardImg></Card>
          ) : ("")}
          <br/>
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