import React, { Component } from 'react'
import AppNavbar from '../common/AppNavbar'
import { Link } from 'react-router-dom'
import { Button, Container } from 'reactstrap'
import { FormattedMessage } from 'react-intl'

class Home extends Component {

  render() {
    return (
      <div>
        <AppNavbar />
        <Container fluid>
          <Button color="link">
            <Link to="/cds">
              <FormattedMessage
                id="Home.manageCDs"
                defaultMessage="Manage CDs" />
            </Link>
          </Button>
        </Container>
        <Container fluid>
          <Button color="link">
            <Link to="/cdsForeign">
              <FormattedMessage
                id="Home.manageForeignCDs"
                defaultMessage="Manage Foreign CDs" />
            </Link>
          </Button>
        </Container>
        <Container fluid>
          <Button color="link">
            <Link to="/cdsDomestic">
              <FormattedMessage
                id="Home.manageDomesticCDs"
                defaultMessage="Manage Domestic CDs" />
            </Link>
          </Button>
        </Container>
        <Container fluid>
          <Button color="link">
            <Link to="/drumsticks">
              <FormattedMessage
                id="Home.manageDrumsticks"
                defaultMessage="Manage Drumsticks CDs" />
            </Link>
          </Button>
        </Container>
      </div>
    )
  }
}

export default Home