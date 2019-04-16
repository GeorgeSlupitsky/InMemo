import React, { Component } from 'react'
import { Link, withRouter } from 'react-router-dom'
import { Button, Container, FormGroup, Input, Label } from 'reactstrap'
import AppNavbar from '../../common/AppNavbar'
import { FormattedMessage } from 'react-intl'
import { AvForm, AvGroup, AvInput, AvFeedback, AvField } from 'availity-reactstrap-validation'
import LocalizedStrings from 'localized-strings'

class CDEdit extends Component {

  emptyItem = {
    band: {
      name: '',
      order: '',
      bandMembers: []
    },
    album: '',
    year: '',
    booklet: '',
    countryEdition: '',
    cdType: '',
    cdGroup: ''
  }

  constructor(props) {
    super(props)
    this.state = {
      bandMember: '',
      item: this.emptyItem,
      booklets: [],
      countries: [],
      types: [],
      groups: [],
      bandOrders: [],
      messages: {}
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleChangeForBand = this.handleChangeForBand.bind(this)
    this.handleChangeForBandMember = this.handleChangeForBandMember.bind(this)
    this.handleSave = this.handleSave.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.onEnterBandMember = this.onEnterBandMember.bind(this)
    this.removeBandMemberFromArray = this.removeBandMemberFromArray.bind(this)
  }

  async componentDidMount() {
    let messages = new LocalizedStrings({
      en: {
        errorMessageYearRequired: 'Please enter a year or "-"',
        errorMessageYearPattern: 'Year must be within 1900-2099',
        errorMessageBooklet: 'Please choose booklet',
        errorMessageCountry: 'Please choose country',
        errorMessageType: 'Please choose type',
        errorMessageGroup: 'Please choose group',
        errorMessageOrder: 'Please choose order',
        selectLabelBooklet: 'Booklet',
        selectLabelCountry: 'Country',
        selectLabelType: 'Type',
        selectLabelGroup: 'Group',
        selectLabelOrder: 'Order'
      },
      es: {
        errorMessageYearRequired: 'Por favor ingrese un año o "-"',
        errorMessageYearPattern: 'El año debe estar dentro de 1900-2099',
        errorMessageBooklet: 'Por favor, elija el folleto',
        errorMessageCountry: 'Por favor, elija el país',
        errorMessageType: 'Por favor, elija el tipo',
        errorMessageGroup: 'Por favor, elija el grupo',
        errorMessageOrder: 'Por favor, elija orden',
        selectLabelBooklet: 'Folleto',
        selectLabelCountry: 'Pais',
        selectLabelType: 'Tipo',
        selectLabelGroup: 'Grupo',
        selectLabelOrder: 'Orden'
      },
      ru: {
        errorMessageYearRequired: 'Пожалуйста, укажите год или "-"',
        errorMessageYearPattern: 'Год должен быть в пределах 1900-2099',
        errorMessageBooklet: 'Пожалуйста, укажите буклет',
        errorMessageCountry: 'Пожалуйста, укажите страну',
        errorMessageType: 'Пожалуйста, укажите тип',
        errorMessageGroup: 'Пожалуйста, укажите класс',
        errorMessageOrder: 'Пожалуйста, укажите порядок',
        selectLabelBooklet: 'Буклет',
        selectLabelCountry: 'Страна',
        selectLabelType: 'Тип',
        selectLabelGroup: 'Класс',
        selectLabelOrder: 'Порядок'
      },
      uk: {
        errorMessageYearRequired: 'Будь ласка, вкажіть рік або "-"',
        errorMessageYearPattern: 'Рік має бути в межах 1900-2099',
        errorMessageBooklet: 'Будь ласка, вкажіть буклет',
        errorMessageCountry: 'Будь ласка, вкажіть країну',
        errorMessageType: 'Будь ласка, вкажіть тип',
        errorMessageGroup: 'Будь ласка, вкажіть клас',
        errorMessageOrder: 'Будь ласка, вкажіть порядок',
        selectLabelBooklet: 'Буклет',
        selectLabelCountry: 'Країна',
        selectLabelType: 'Тип',
        selectLabelGroup: 'Клас',
        selectLabelOrder: 'Порядок'
      },
      ja: {
        errorMessageYearRequired: '年または「 - 」を入力してください',
        errorMessageYearPattern: '年は1900-2099以内でなければなりません',
        errorMessageBooklet: '小冊子を選択してください',
        errorMessageCountry: '国を選択してください',
        errorMessageType: 'タイプを選択してください',
        errorMessageGroup: 'グループを選択してください',
        errorMessageOrder: '注文を選択してください',
        selectLabelBooklet: '小冊子',
        selectLabelCountry: '国',
        selectLabelType: 'タイプ',
        selectLabelGroup: 'グループ',
        selectLabelOrder: '注文'
      }
    })

    this.setState({messages: messages})

    if (this.props.match.params.id !== 'new') {
      const cd = await (await fetch(`/api/cd/${this.props.match.params.id}`)).json()
      this.setState({ item: cd })
    }
    const booklets = await (await fetch(`/api/enums/cds/booklets/`)).json()
    const countries = await (await fetch(`/api/enums/cds/countries/`)).json()
    const types = await (await fetch(`/api/enums/cds/types/`)).json()
    const groups = await (await fetch(`/api/enums/cds/groups/`)).json()
    const bandOrders = await (await fetch(`/api/enums/cds/band/orders`)).json()
    this.setState({
      booklets: booklets,
      countries: countries,
      types: types,
      groups: groups,
      bandOrders: bandOrders
    })

  }

  handleChange(event) {
    const { value, name } = event.target
    let item = { ...this.state.item }
    item[name] = value
    this.setState({ item })
  }

  handleChangeForBand(event) {
    const { value, id, name } = event.target
    let item = { ...this.state.item }
    item[id][name] = value
    this.setState({ item })
  }

  handleChangeForBandMember(event) {
    const { value } = event.target
    this.setState({ bandMember: value })
  }

  async handleSave() {
    const { item } = this.state
    await fetch('/api/cd', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    })
    this.props.history.push(this.props.url)
  }

  handleSubmit(event, errors) {
    if (errors.length === 0) {
      this.handleSave()
    }
  }

  onEnterBandMember(event) {
    if (event.key === 'Enter') {
      event.preventDefault()
      const { value } = event.target
      let item = { ...this.state.item }
      let member = { name: value }
      item.band.bandMembers.push(member)
      this.setState(item)
      this.setState({ bandMember: '' })
    }
  }

  removeBandMemberFromArray(arr, value) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i].name === value) {
        arr.splice(i, 1)
        break
      }
    }
    let item = { ...this.state.item }
    item.band.bandMembers = arr
    this.setState(item)
  }

  render() {

    const { item, booklets, countries, types, groups, bandOrders, messages } = this.state

    const title = <h2>{item.id ? (
      <FormattedMessage id="CDEdit.edit" defaultMessage="Edit CD" />
    ) : (
        <FormattedMessage id="CDEdit.add" defaultMessage="Add CD" />)}</h2>

    let optionBookletItems = booklets.map((booklet) =>
      <option key={booklet.id} value={booklet.cdBookletEnum}>{booklet.name}</option>
    )

    let optionCountryItems = countries.map((country) =>
      <option key={country.id} value={country.cdCountryEnum}>{country.name}</option>
    )

    let optionTypeItems = types.map((type) =>
      <option key={type.id} value={type.cdTypeEnum}>{type.name}</option>
    )

    let optionGroupItems = groups.map((group) =>
      <option key={group.id} value={group.cdGroupEnum}>{group.name}</option>
    )

    let optionBandOrderItems = bandOrders.map((bandOrder) =>
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
                required: { value: true, errorMessage: messages.errorMessageYearRequired },
                pattern: { value: '^[-]$|(19|20)[0-9][0-9]$', errorMessage: messages.errorMessageYearPattern }
              }} />
          </AvGroup>
          <FormGroup>
            <Label for="bandMambers">
              <FormattedMessage id="CDEdit.bandMembers" defaultMessage="Band Members" />
            </Label>
            <Input type="text" name="bandMambers" id="bandMembers" value={this.state.bandMember}
              onChange={this.handleChangeForBandMember} onKeyPress={this.onEnterBandMember} />
          </FormGroup>
          {bandMembers}
          <div className="row">
            <AvGroup className="col-md-2 mb-3">
              <AvField type="select" name="booklet" id="booklet" value={item.booklet || ''}
                label={messages.selectLabelBooklet}
                onChange={this.handleChange} validate={{
                  required: { value: true, errorMessage: messages.errorMessageBooklet }
                }}>
                <option value="" disabled>--</option>
                {optionBookletItems}
              </AvField>
            </AvGroup>
            <AvGroup className="col-md-2 mb-3">
              <AvField type="select" name="countryEdition" id="countryEdition" value={item.countryEdition || ''}
                label={messages.selectLabelCountry}
                onChange={this.handleChange} validate={{
                  required: { value: true, errorMessage: messages.errorMessageCountry }
                }}>
                <option value="" disabled>--</option>
                {optionCountryItems}
              </AvField>
            </AvGroup>
            <AvGroup className="col-md-3 mb-3">
              <AvField type="select" name="cdType" id="cdType" value={item.cdType || ''}
                label={messages.selectLabelType}
                onChange={this.handleChange} validate={{
                  required: { value: true, errorMessage: messages.errorMessageType }
                }}>
                <option value="" disabled>--</option>
                {optionTypeItems}
              </AvField>
            </AvGroup>
            <AvGroup className="col-md-3 mb-3">
              <AvField type="select" name="cdGroup" id="cdGroup" value={item.cdGroup || ''}
                label={messages.selectLabelGroup}
                onChange={this.handleChange} validate={{
                  required: { value: true, errorMessage: messages.errorMessageGroup }
                }}>
                <option value="" disabled>--</option>
                {optionGroupItems}
              </AvField>
            </AvGroup>
            <AvGroup className="col-md-2 mb-3">
              <AvField type="select" name="order" id="band" value={item.band.order || ''}
                label={messages.selectLabelOrder}
                onChange={this.handleChangeForBand} validate={{
                  required: { value: true, errorMessage: messages.errorMessageOrder }
                }}>
                <option value="" disabled>--</option>
                {optionBandOrderItems}
              </AvField>
            </AvGroup>
          </div>
          <FormGroup>
            <Button color="primary">
              <FormattedMessage id="CDEdit.save" defaultMessage="Save" />
            </Button>{' '}
            <Button color="secondary" tag={Link} to={this.props.url}>
              <FormattedMessage id="CDEdit.cancel" defaultMessage="Cancel" />
            </Button>
          </FormGroup>
        </AvForm>
      </Container>
    </div>
  }
}

export default withRouter(CDEdit)