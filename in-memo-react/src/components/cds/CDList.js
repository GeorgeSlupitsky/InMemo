import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../../common/AppNavbar';
import { Link } from 'react-router-dom';

class CDList extends Component{

    constructor(props){
        super(props);
        this.state = {cds: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount(){
        this.setState({isLoading: true});
        fetch('api/cds')
            .then(response => response.json())
            .then(data => this.setState({cds: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/api/cd/${id}`, {
          method: 'DELETE',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        }).then(() => {
          let updatedCds = [...this.state.cds].filter(i => i.id !== id);
          this.setState({cds: updatedCds});
        });
      }

    render(){
        const {cds, isLoading} = this.state;

        if (isLoading){
            return <p>Loading...</p>;
        }

        const cdList = cds.map(cd => {
            return <tr key={cd.id}>
                <td>{cd.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{cd.band.name}</td>
                <td>{cd.album}</td>
                <td>{cd.year}</td>
                <td>{cd.booklet}</td>
                <td>{cd.countryEdition}</td>
                <td>{cd.cdType}</td>
                {/* <td>{cd.band.bandMembers}</td> */}
                <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/cds/" + cd.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(cd.id)}>Delete</Button>
                </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
              <AppNavbar/>
              <Container fluid>
                <div className="float-right">
                  <Button color="success" tag={Link} to="/cds/new">Add CD</Button>
                </div>
                <h3>My CD Collection</h3>
                <Table className="mt-4">
                  <thead>
                  <tr>
                    <th width="2%">№</th>
                    <th width="15%">Band</th>
                    <th width="15%">Album</th>
                    <th width="10%">Year</th>
                    <th width="10%">Booklet</th>
                    <th width="10%">Country</th>
                    <th width="10%">Type</th>
                    {/* <th width="20%">Members</th> */}
                    <th width="5%">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  {cdList}
                  </tbody>
                </Table>
              </Container>
            </div>
          );
    }
}

export default CDList;