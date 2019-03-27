import React, { Component } from 'react';
import '../../app/App.css';
import AppNavbar from '../../common/AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
  render() {
    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <Button color="link"><Link to="/cds">Manage CDs</Link></Button>
        </Container>
        <Container fluid>
          <Button color="link"><Link to="/cdsForeign">Manage Foreign CDs</Link></Button>
        </Container>
        <Container fluid>
          <Button color="link"><Link to="/cdsDomestic">Manage Domestic CDs</Link></Button>
        </Container>
        <Container fluid>
          <Button color="link"><Link to="/drumsticks">Manage Drumsticks</Link></Button>
        </Container>
      </div>
    );
  }
}

export default Home;