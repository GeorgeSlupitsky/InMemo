import React, { Component } from 'react';
import { Collapse, Button, ButtonGroup, Container, Table, Form, FormGroup, Input, Label, Card, CardBody, Nav, NavItem, NavLink } from 'reactstrap';
import AppNavbar from '../../common/AppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class CDList extends Component{

    constructor(props){
        super(props);
        this.state = {
          cds: [], 
          isLoading: true,
          collapseUpload: false,
          collapseDownload: false,
          file: null,
          rewriteDB: false
        };
        this.toggle = this.toggle.bind(this);
        this.remove = this.remove.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.fileUpload = this.fileUpload.bind(this);
        this.onChange = this.onChange.bind(this)
        this.downloadFile = this.downloadFile.bind(this)
    }

    componentDidMount(){
        this.setState({isLoading: true});
        if (this.props.group === 'all'){
          fetch('api/cds')
          .then(response => response.json())
          .then(data => this.setState({cds: data, isLoading: false}))
        } else if (this.props.group === 'foreign'){
          fetch('api/cdsForeign')
          .then(response => response.json())
          .then(data => this.setState({cds: data, isLoading: false}));
        } else {
          fetch('api/cdsDomestic')
          .then(response => response.json())
          .then(data => this.setState({cds: data, isLoading: false}));
        }
        
    }

    toggle(button) {
      if (button === "upload"){
        if (this.state.collapseDownload){
          this.setState(state => ({
            collapseDownload: !state.collapseDownload
          }));
        }
        this.setState(state => ({ 
          collapseUpload: !state.collapseUpload
        }));
      } else if (button === "download"){
        if (this.state.collapseUpload){
          this.setState(state => ({
            collapseUpload: !state.collapseUpload
          }))
        }
        this.setState(state => ({ 
          collapseDownload: !state.collapseDownload
        }));
      }
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

    handleSubmit(e) {
      e.preventDefault()
      this.fileUpload(this.state.file)
    }

    fileUpload(file){
      const formData = new FormData();
      this.setState({isLoading: true});
      formData.append('file', file)
      formData.append('rewriteDB', this.state.rewriteDB)
      fetch('/api/cd/upload',{
        method: 'POST',
        body: formData
      }).then(
        response => {
          if (response.ok){
            fetch('/api/cds')
            .then(response => response.json())
            .then(data => this.setState({cds: data, isLoading: false}))
          }
        }
      )
    }

    onChange(e){
      const {files} = e.target
      this.setState({file:files[0]})
    }

    handleChange(e){
      this.setState({rewriteDB: !this.state.rewriteDB})
    }

    downloadFile(ext){
      fetch('/api/export/downloadCD.' + ext, {
        method: 'GET'
      }).then(response => response.blob())
      .then(blob => {
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = "downloadCD." + ext;
        document.body.appendChild(a);
        a.click();    
        a.remove();
      })
    }

    render(){
        const {cds, isLoading} = this.state;

        if (isLoading){
            return <p>Loading...</p>;
        }

        let number = 1;

        const cdList = cds.map(cd => {
            return <tr key={cd.id}>
                <td>{number++}</td>
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

        let cdCollection = ''

        if (this.props.group === 'all'){
          cdCollection = 'My CD Collection'
        } else if (this.props.group === 'foreign'){
          cdCollection = 'My Foreign CD Collection'
        } else {
          cdCollection = 'My Domestic CD Collection'
        }

        return (
            <div>
              <AppNavbar/>
              <Container fluid>
                <h3>{cdCollection}</h3>
                <div className="float-left">
                  <ButtonGroup>
                    <Button color="outline-success" size="lg" tag={Link} to="/cds/new">Add CD</Button>
                    <Button color="outline-info" size="lg" onClick={() => this.toggle("upload")}>Upload CDs</Button>
                    <Button color="outline-primary" size="lg" onClick={() => this.toggle("download")}>Download</Button>
                  </ButtonGroup>
                  <Collapse isOpen={this.state.collapseUpload}>
                    <Form onSubmit={this.handleSubmit}>
                      <FormGroup>
                        <Card>
                          <CardBody>
                            <Label for="upload">Excel file:</Label>
                            <Input type="file" name="upload" onChange={this.onChange}/>
                            <br/>
                            <Input type="checkbox" name="rewriteDB" checked={this.state.rewriteDB} onChange={this.handleChange} style={{marginLeft: 5}}/>
                            <Label for="rewriteDB" style={{marginLeft: 25}}>Do you want to rewrite DB?</Label>
                            <br/>
                            <Button color="primary" type="submit">Upload</Button>
                          </CardBody>
                        </Card>
                      </FormGroup>
                    </Form>
                  </Collapse>
                  <Collapse isOpen={this.state.collapseDownload}>
                    <Form onSubmit={this.handleSubmit}>
                      <FormGroup>
                        <Card>
                          <CardBody>
                            <Nav vertical>
                              <NavItem>
                                <NavLink href="#" onClick={() => this.downloadFile("xls")}>XLS</NavLink>
                              </NavItem>
                              <NavItem>
                                <NavLink href="#" onClick={() => this.downloadFile("xlsx")}>XLSX</NavLink>
                              </NavItem>
                              <NavItem>
                                <NavLink href="#" onClick={() => this.downloadFile("pdf")}>PDF</NavLink>
                              </NavItem>
                              <NavItem>
                                <NavLink href="#" onClick={() => this.downloadFile("csv")}>CSV</NavLink>
                              </NavItem>
                            </Nav>
                          </CardBody>
                        </Card>
                      </FormGroup>
                    </Form>
                  </Collapse>
                </div>
                <br/>
                <br/>
                <Table striped bordered hover className="mt-4">
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