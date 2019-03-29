import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../../common/AppNavbar';

class CDEdit extends Component {

  emptyItem = {
    band: {
      name: '',
      order: '',
      bandMembers: [{
        name: ''
      }]
    },
    album: '',
    year: '',
    booklet: '',
    countryEdition: '',
    cdType: '',
    cdGroup: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      booklets: [],
      countries: [],
      types: [],
      groups: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleChangeForBand = this.handleChangeForBand.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const cd = await (await fetch(`/api/cd/${this.props.match.params.id}`)).json();
      this.setState({item: cd});
    }
    const booklets = await (await fetch(`/api/cds/booklets/`)).json();
    const countries = await (await fetch(`/api/cds/countries/`)).json();
    const types = await (await fetch(`/api/cds/types/`)).json();
    const groups = await (await fetch(`/api/cds/groups/`)).json();
    this.setState({
      booklets: booklets,
      countries: countries,
      types: types,
      groups: groups
    })
  }

  handleChange(event) {
    const {value, name} = event.target;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  handleChangeForBand(event) {
    const {value, id, name} = event.target;
    let item = {...this.state.item};
    item[id][name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    console.log(item)
    await fetch('/api/cd', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/cds');
  }

  render() {
    const {item, booklets, countries, types, groups} = this.state;
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

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Band</Label>
            <Input type="text" name="name" id="band" value={item.band.name}
                   onChange={this.handleChangeForBand}/>
          </FormGroup>
          <FormGroup>
            <Label for="album">Album</Label>
            <Input type="text" name="album" id="album" value={item.album || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="year">Year</Label>
            <Input type="text" name="year" id="year" value={item.year || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
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
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/cds">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(CDEdit);