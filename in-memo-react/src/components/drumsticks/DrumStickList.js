import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from '../../common/AppNavbar';
import { Link } from 'react-router-dom';

class DrumStickList extends Component{

    constructor(props){
        super(props);
        this.state = {drumsticks: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount(){
        this.setState({isLoading: true});

        fetch('api/drumsticks')
            .then(response => response.json())
            .then(data => this.setState({drumsticks: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/api/drumstick/${id}`, {
          method: 'DELETE',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        }).then(() => {
          let updatedDrumsticks = [...this.state.drumsticks].filter(i => i.id !== id);
          this.setState({drumsticks: updatedDrumsticks});
        });
      }

    render(){
        const {drumsticks, isLoading} = this.state;

        if (isLoading){
            return <p>Loading...</p>;
        }

        const drumStickList = drumsticks.map(drumstick => {
            return <tr key={drumstick.id}>
                <td><input type="checkbox"></input></td>
                <td>{drumstick.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{drumstick.band}</td>
                <td>{drumstick.drummerName}</td>
                <td>{drumstick.date}</td>
                <td>{drumstick.city}</td>
                <td>{drumstick.description}</td>
                <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/drumsticks/" + drumstick.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(drumstick.id)}>Delete</Button>
                </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
              <AppNavbar/>
              <Container fluid>
                <div className="float-right">
                  <Button color="success" tag={Link} to="/drumsticks/new">Add Drumstick</Button>
                </div>
                <h3>My Drumstick Collection</h3>
                <Table className="mt-4">
                  <thead>
                  <tr>
                    <th width="2%"></th>
                    <th width="2%">№</th>
                    <th width="20%">Band</th>
                    <th width="26%">Drummer Name</th>
                    <th width="15%">Date</th>
                    <th width="15%">City</th>
                    <th width="15%">Description</th>
                    <th width="5%">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  {drumStickList}
                  </tbody>
                </Table>
              </Container>
            </div>
          );
    }
}

export default DrumStickList;