import React from 'react'
import { ServiceContextConsumer } from '../components/service-context/ServiceContext'

const withService = () => (Wrapped) => {
    return (props) => {
        return (
            <ServiceContextConsumer>
                {
                    (service) => {
                        return (
                            <Wrapped {...props} service={service}/>
                        )
                    }
                }
            </ServiceContextConsumer>
        )
    }
}

export default withService
