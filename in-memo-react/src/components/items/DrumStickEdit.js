import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../../common/AppNavbar';

class DrumStickEdit extends Component {

  emptyItem = {
    band: '',
    drummerName: '',
    date: '',
    city: 'LVIV',
    description: 'ONE',
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      cities: [],
      descriptions: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const drumstick = await (await fetch(`/api/drumstick/${this.props.match.params.id}`)).json();
      this.setState({ item: drumstick });
    }
    const cities = await (await fetch(`/api/enums/drumsticks/cities/`)).json();
    const descriptions = await (await fetch(`/api/enums/drumstick/types/`)).json();
    this.setState({
      cities: cities,
      descriptions: descriptions
    })
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { item } = this.state;

    await fetch('/api/drumstick', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/drumsticks');
  }

  render() {
    const { item, cities, descriptions } = this.state;
    const title = <h2>{item.id ? 'Edit Drumstick' : 'Add Drumstick'}</h2>;

    let optionCityItems = cities.map((city) =>
      <option key={city.id} value={city.drumStickCityEnum}>{city.name}</option>
    );

    let optionDescriptionItems = descriptions.map((description) =>
      <option key={description.id} value={description.drumStickTypeEnum}>{description.name}</option>
    );

    return <div>
      <AppNavbar />
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="band">Band</Label>
            <Input type="text" name="band" id="band" value={item.band || ''}
              onChange={this.handleChange} autoComplete="name" />
          </FormGroup>
          <FormGroup>
            <Label for="drummerName">Drummer Name</Label>
            <Input type="text" name="drummerName" id="drummerName" value={item.drummerName || ''}
              onChange={this.handleChange} autoComplete="address-level1" />
          </FormGroup>
          <FormGroup>
            <Label for="date">Date</Label>
            <Input type="text" name="date" id="date" value={item.date || ''}
              onChange={this.handleChange} autoComplete="address-level1" />
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-6 mb-3">
              <Label for="city">City</Label>
              <Input type="select" name="city" id="city" value={item.city || ''}
                onChange={this.handleChange} autoComplete="address-level1">
                {optionCityItems}
              </Input>
            </FormGroup>
            <FormGroup className="col-md-6 mb-3">
              <Label for="description">Description</Label>
              <Input type="select" name="description" id="description" value={item.description || ''}
                onChange={this.handleChange} autoComplete="address-level1">
                {optionDescriptionItems}
              </Input>
            </FormGroup>
          </div>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to='/drumsticks'>Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(DrumStickEdit);