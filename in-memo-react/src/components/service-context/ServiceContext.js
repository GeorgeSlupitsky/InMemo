import * as React from "react"

const {
    Provider: ServiceContextProvider,
    Consumer: ServiceContextConsumer
} = React.createContext()

export {
    ServiceContextProvider,
    ServiceContextConsumer
}