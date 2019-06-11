import React, { Component } from 'react'
import { FormattedMessage } from 'react-intl'
import { Collapse, Button, ButtonGroup, Form, FormGroup, Input, Label, Card, CardBody, Nav, NavItem, NavLink } from 'reactstrap'
import { Link } from 'react-router-dom'

export default class ButtonsPanel extends Component {

    constructor(props) {
        super(props)
        this.toggleModal = this.toggleModal.bind(this)
        this.onChangeFile = this.onChangeFile.bind(this)
        this.handleSubmitUpload = this.handleSubmitUpload.bind(this)
    }

    toggleModal(button) {
        const { collapseDownload, collapseUpload } = this.props
        if (button === "upload") {
            if (collapseDownload) {
                this.props.toggleDownloadModal()
            }
            this.props.toggleUploadModal()
        } else if (button === "download") {
            if (collapseUpload) {
                this.props.toggleUploadModal()
            }
            this.props.toggleDownloadModal()
        }
    }

    onChangeFile(e) {
        const { files } = e.target
        this.props.uploadFile(files[0])
    }

    handleSubmitUpload(e) {
        const { service, collection, file, rewriteDB } = this.props
        e.preventDefault()
        let uploadURL
        if (collection === 'cds') {
            uploadURL = '/api/cd/upload'
        } else if (collection === 'drumsticks') {
            uploadURL = '/api/drumstick/upload'
        }
        service.uploadFile(file, rewriteDB, uploadURL)
            .then(response => {
                if (response.status === 200 && rewriteDB) {
                    window.location.reload()
                }
            })
    }

    render() {
        let getURL, fileName, add, upload
        const { collection,
            service,
            drumstickCheckedIds,
            localizedMessages,
            rewriteDB,
            addLink,
            collapseDownload,
            collapseUpload,
            toggleRewriteDB } = this.props

        if (collection === 'cds') {
            getURL = '/api/export/downloadCD.'
            fileName = localizedMessages.fileNameCDs
            add = <FormattedMessage id="ButtonsPanel.cdAdd" defaultMessage="Add CD" />
            upload = <FormattedMessage id="ButtonsPanel.cdUpload" defaultMessage="Upload CDs" />
        } else if (collection === 'drumsticks') {
            getURL = '/api/export/downloadDrumStick.'
            fileName = localizedMessages.fileNameDrumSticks
            add = <FormattedMessage id="ButtonsPanel.drumstickAdd" defaultMessage="Add Drumstick" />
            upload = <FormattedMessage id="ButtonsPanel.drumstickUpload" defaultMessage="Upload Drumsticks" />
        }

        return (
            <div className="float-left">
                <ButtonGroup>
                    <Button color="outline-success" size="lg" tag={Link} to={addLink}>
                        {add}
                    </Button>
                    <Button color="outline-info" size="lg" onClick={() => this.toggleModal('upload')}>
                        {upload}
                    </Button>
                    <Button color="outline-primary" size="lg" onClick={() => this.toggleModal('download')}>
                        <FormattedMessage id="ButtonsPanel.download" defaultMessage="Download" />
                    </Button>
                    {collection === 'drumsticks' ? (
                        <Button color="outline-warning" size="lg" onClick={() => service.printDrumStickLabels(drumstickCheckedIds, localizedMessages.fileNameDrumSticksLabels)}>
                            <FormattedMessage id="ButtonsPanel.print" defaultMessage="Print" />
                        </Button>
                    ) : null}
                </ButtonGroup>
                <Collapse isOpen={collapseUpload}>
                    <Form onSubmit={this.handleSubmitUpload}>
                        <FormGroup>
                            <Card>
                                <CardBody>
                                    <Label for="upload">
                                        <FormattedMessage id="ButtonsPanel.excelFile" defaultMessage="Excel file:" />
                                    </Label>
                                    <Input type="file" name="upload" onChange={this.onChangeFile} />
                                    <br />
                                    <Input type="checkbox" name="rewriteDB" checked={rewriteDB} onChange={() => toggleRewriteDB()} style={{ marginLeft: 5 }} />
                                    <Label for="rewriteDB" style={{ marginLeft: 25 }}>
                                        <FormattedMessage id="ButtonsPanel.rewriteDB" defaultMessage="Rewrite DB?" />
                                    </Label>
                                    <br />
                                    <Button color="primary" type="submit">
                                        <FormattedMessage id="ButtonsPanel.upload" defaultMessage="Upload" />
                                    </Button>
                                </CardBody>
                            </Card>
                        </FormGroup>
                    </Form>
                </Collapse>
                <Collapse isOpen={collapseDownload}>
                    <Form>
                        <FormGroup>
                            <Card>
                                <CardBody>
                                    <Nav vertical>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => service.downloadFile('xls', getURL, fileName)}>XLS</NavLink>
                                        </NavItem>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => service.downloadFile('xlsx', getURL, fileName)}>XLSX</NavLink>
                                        </NavItem>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => service.downloadFile('pdf', getURL, fileName)}>PDF</NavLink>
                                        </NavItem>
                                        <NavItem>
                                            <NavLink href="#" onClick={() => service.downloadFile('csv', getURL, fileName)}>CSV</NavLink>
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
