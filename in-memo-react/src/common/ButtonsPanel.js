import React, { Component } from 'react';
import { Collapse, Button, ButtonGroup, Form, FormGroup, Input, Label, Card, CardBody, Nav, NavItem, NavLink } from 'reactstrap';
import { Link } from 'react-router-dom';

class ButtonsPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            collapseUpload: false,
            collapseDownload: false,
            file: null,
            rewriteDB: false
        }
        this.toggle = this.toggle.bind(this)
        this.onChangeFile = this.onChangeFile.bind(this)
        this.handleChangeCheckbox = this.handleChangeCheckbox.bind(this)
        this.handleSubmitUpload = this.handleSubmitUpload.bind(this)
        this.fileUpload = this.fileUpload.bind(this)
        this.downloadFile = this.downloadFile.bind(this)
        this.printLabels = this.printLabels.bind(this)
    }

    toggle(button) {
        if (button === "upload") {
            if (this.state.collapseDownload) {
                this.setState(state => ({
                    collapseDownload: !state.collapseDownload
                }));
            }
            this.setState(state => ({
                collapseUpload: !state.collapseUpload
            }));
        } else if (button === "download") {
            if (this.state.collapseUpload) {
                this.setState(state => ({
                    collapseUpload: !state.collapseUpload
                }))
            }
            this.setState(state => ({
                collapseDownload: !state.collapseDownload
            }));
        }
    }

    onChangeFile(e) {
        const { files } = e.target
        this.setState({ file: files[0] })
    }

    handleChangeCheckbox(e) {
        this.setState({ rewriteDB: !this.state.rewriteDB })
    }

    handleSubmitUpload(e) {
        e.preventDefault()
        if (this.props.collection === "cds") {
            this.fileUpload(this.state.file, '/api/cd/upload')
        } else if (this.props.collection === "drumsticks") {
            this.fileUpload(this.state.file, '/api/drumstick/upload')
        }
    }

    fileUpload(file, postURL) {
        const formData = new FormData();
        formData.append('file', file)
        formData.append('rewriteDB', this.state.rewriteDB)
        fetch(postURL, {
            method: 'POST',
            body: formData
        }).then(
            response => {
                if (response.ok) {
                    this.props.refreshPage()
                }
            }
        )
    }

    downloadFile(ext, getURL, fileName) {
        fetch(getURL + ext, {
            method: 'GET'
        }).then(response => response.blob())
            .then(blob => {
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = fileName + ext;
                document.body.appendChild(a);
                a.click();
                a.remove();
            })
    }

    printLabels(checkedIds) {
        fetch(`/api/export/downloadDrumSticksLabels.pdf`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(checkedIds)
        }).then(response => response.blob())
            .then(blob => {
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = "printLabels.pdf";
                document.body.appendChild(a);
                a.click();
                a.remove();
            })
    }

    render() {
        let getURL, fileName, addLink, add, upload
        const { collection } = this.props
        if (collection === 'cds') {
            getURL = '/api/export/downloadCD.'
            fileName = 'downloadCD.'
            add = 'Add CD'
            upload = 'Upload CDs'
        } else if (collection === 'drumsticks') {
            getURL = '/api/export/downloadDrumStick.'
            fileName = 'downloadDrumStick.'
            add = 'Add Drumstick'
            upload = 'Upload DrumSticks'
        }
        return (
            <div className="float-left">
                <ButtonGroup>
                    <Button color="outline-success" size="lg" tag={Link} to={this.props.addLink}>{add}</Button>
                    <Button color="outline-info" size="lg" onClick={() => this.toggle('upload')}>{upload}</Button>
                    <Button color="outline-primary" size="lg" onClick={() => this.toggle('download')}>Download</Button>
                    {collection === 'drumsticks' ? (
                        <Button color="outline-warning" size="lg" onClick={() => this.printLabels(this.props.drumstickCheckedIds)}>Print</Button>
                    ) : null}
                </ButtonGroup>
                <Collapse isOpen={this.state.collapseUpload}>
                    <Form onSubmit={this.handleSubmitUpload}>
                        <FormGroup>
                            <Card>
                                <CardBody>
                                    <Label for="upload">Excel file:</Label>
                                    <Input type="file" name="upload" onChange={this.onChangeFile} />
                                    <br />
                                    <Input type="checkbox" name="rewriteDB" checked={this.state.rewriteDB} onChange={this.handleChangeCheckbox} style={{ marginLeft: 5 }} />
                                    <Label for="rewriteDB" style={{ marginLeft: 25 }}>Do you want to rewrite DB?</Label>
                                    <br />
                                    <Button color="primary" type="submit">Upload</Button>
                                </CardBody>
                            </Card>
                        </FormGroup>
                    </Form>
                </Collapse>
                <Collapse isOpen={this.state.collapseDownload}>
                    <Form>
                        <FormGroup>
                            <Card>
                                <CardBody>
                                    <Nav vertical>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => this.downloadFile('xls', getURL, fileName)}>XLS</NavLink>
                                        </NavItem>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => this.downloadFile('xlsx', getURL, fileName)}>XLSX</NavLink>
                                        </NavItem>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => this.downloadFile('pdf', getURL, fileName)}>PDF</NavLink>
                                        </NavItem>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => this.downloadFile('csv', getURL, fileName)}>CSV</NavLink>
                                        </NavItem>
                                    </Nav>
                                </CardBody>
                            </Card>
                        </FormGroup>
                    </Form>
                </Collapse>
            </div>
        )
    }
}
export default ButtonsPanel