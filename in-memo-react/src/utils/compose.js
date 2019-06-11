const compose = (...funcs) => (item) => {
    return funcs.reduceRight(
        (previousValue, currentFunc) => currentFunc(previousValue),
        item
    )
}

export default compose