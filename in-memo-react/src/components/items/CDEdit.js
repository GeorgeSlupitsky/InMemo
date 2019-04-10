import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../../common/AppNavbar';

class CDEdit extends Component {

  emptyItem = {
    band: {
      name: '',
      order: 'MAIN',
      bandMembers: []
    },
    album: '',
    year: '',
    booklet: 'WITH_OUT',
    countryEdition: 'UKRAINE',
    cdType: 'NUMBER',
    cdGroup: 'FOREIGN'
  };

  constructor(props) {
    super(props);
    this.state = {
      bandMember: '',
      item: this.emptyItem,
      booklets: [],
      countries: [],
      types: [],
      groups: [],
      bandOrders: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleChangeForBand = this.handleChangeForBand.bind(this);
    this.handleChangeForBandMember = this.handleChangeForBandMember.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.onEnterBandMember = this.onEnterBandMember.bind(this)
    this.arrayRemove = this.arrayRemove.bind(this)
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const cd = await (await fetch(`/api/cd/${this.props.match.params.id}`)).json();
      this.setState({ item: cd });
    }
    const booklets = await (await fetch(`/api/enums/cds/booklets/`)).json();
    const countries = await (await fetch(`/api/enums/cds/countries/`)).json();
    const types = await (await fetch(`/api/enums/cds/types/`)).json();
    const groups = await (await fetch(`/api/enums/cds/groups/`)).json();
    const bandOrders = await (await fetch(`/api/enums/cds/band/orders`)).json();
    this.setState({
      booklets: booklets,
      countries: countries,
      types: types,
      groups: groups,
      bandOrders: bandOrders
    })
  }

  handleChange(event) {
    const { value, name } = event.target;
    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });
  }

  handleChangeForBand(event) {
    const { value, id, name } = event.target;
    let item = { ...this.state.item };
    item[id][name] = value;
    this.setState({ item });
  }

  onEnterBandMember(event) {
    if (event.key === 'Enter') {
      const { value } = event.target;
      let item = { ...this.state.item };
      let member = { name: value }
      item.band.bandMembers.push(member)
      this.setState(item)
      this.setState({ bandMember: '' })
    }
  }

  handleChangeForBandMember(event) {
    const { value } = event.target
    this.setState({ bandMember: value })
  }

  async handleSave() {
    const { item } = this.state;
    await fetch('/api/cd', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push(this.props.url);
  }

  arrayRemove(arr, value) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i].name === value) {
        arr.splice(i, 1);
        break;
      }
    }
    let item = { ...this.state.item };
    item.band.bandMembers = arr
    this.setState(item)
  }

  render() {
    const { item, booklets, countries, types, groups, bandOrders } = this.state;

    const title = <h2>{item.id ? 'Edit CD' : 'Add CD'}</h2>;

    let optionBookletItems = booklets.map((booklet) =>
      <option key={booklet.id} value={booklet.cdBookletEnum}>{booklet.name}</option>
    );

    let optionCountryItems = countries.map((country) =>
      <option key={country.id} value={country.cdCountryEnum}>{country.name}</option>
    );

    let optionTypeItems = types.map((type) =>
      <option key={type.id} value={type.cdTypeEnum}>{type.name}</option>
    );

    let optionGroupItems = groups.map((group) =>
      <option key={group.id} value={group.cdGroupEnum}>{group.name}</option>
    );

    let optionBandOrderItems = bandOrders.map((bandOrder) =>
      <option key={bandOrder.id} value={bandOrder.cdBandOrder}>{bandOrder.name}</option>
    );

    let bandMembers = item.band.bandMembers.map((bandMember) => {
      return <p key={bandMember.name}>{bandMember.name + " "}
        <Button size="sm" color="danger" onClick={() => this.arrayRemove(item.band.bandMembers, bandMember.name)}>X</Button>
      </p>
    });

    return <div>
      <AppNavbar />
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Band</Label>
            <Input type="text" name="name" id="band" value={item.band.name}
              onChange={this.handleChangeForBand} />
          </FormGroup>
          <FormGroup>
            <Label for="album">Album</Label>
            <Input type="text" name="album" id="album" value={item.album || ''}
              onChange={this.handleChange} autoComplete="address-level1" />
          </FormGroup>
          <FormGroup>
            <Label for="year">Year</Label>
            <Input type="text" name="year" id="year" value={item.year || ''}
              onChange={this.handleChange} autoComplete="address-level1" />
          </FormGroup>
          <FormGroup>
            <Label for="bandMambers">Band Members</Label>
            <Input type="text" name="bandMambers" id="bandMembers" value={this.state.bandMember}
              onChange={this.handleChangeForBandMember} onKeyPress={this.onEnterBandMember} autoComplete="address-level1" />
          </FormGroup>
          {bandMembers}
          <div className="row">
            <FormGroup className="col-md-2 mb-3">
              <Label for="booklet">Booklet</Label>
              <Input type="select" name="booklet" id="booklet" value={item.booklet || ''}
                onChange={this.handleChange}>
                {optionBookletItems}
              </Input>
            </FormGroup>
            <FormGroup className="col-md-2 mb-3">
              <Label for="countryEdition">Country</Label>
              <Input type="select" name="countryEdition" id="countryEdition" value={item.countryEdition || ''}
                onChange={this.handleChange} autoComplete="address-level1">
                {optionCountryItems}
              </Input>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="cdType">Type</Label>
              <Input type="select" name="cdType" id="cdType" value={item.cdType || ''}
                onChange={this.handleChange} autoComplete="address-level1">
                {optionTypeItems}
              </Input>
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="cdGroup">Group</Label>
              <Input type="select" name="cdGroup" id="cdGroup" value={item.cdGroup || ''}
                onChange={this.handleChange} autoComplete="address-level1">
                {optionGroupItems}
              </Input>
            </FormGroup>
            <FormGroup className="col-md-2 mb-3">
              <Label for="countryEdition">Order</Label>
              <Input type="select" name="order" id="band" value={item.band.order || ''}
                onChange={this.handleChangeForBand} autoComplete="address-level1">
                {optionBandOrderItems}
              </Input>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" onClick={this.handleSave}>Save</Button>{' '}
            <Button color="secondary" tag={Link} to={this.props.url}>Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(CDEdit);