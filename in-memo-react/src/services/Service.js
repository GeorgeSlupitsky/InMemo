import axios from 'axios'

export default class Service {

    downloadFile = async (ext, getUrl, fileName) => {
        await axios.get(getUrl + ext, { responseType: 'blob' })
            .then((blob) => {
                var url = window.URL.createObjectURL(blob.data)
                var a = document.createElement('a')
                a.href = url
                a.download = fileName + ext
                document.body.appendChild(a)
                a.click()
                a.remove()
            })
    }

    uploadFile = async (file, rewriteDB, postURL) => {
        const formData = new FormData()
        formData.append('file', file)
        formData.append('rewriteDB', rewriteDB)
        const res = await axios.post(postURL, formData)
        return res
    }

    uploadPhoto = async (photo, itemId, postURL) => {
        const formData = new FormData()
        formData.append('photo', photo)
        formData.append('drumstickId', itemId)
        const res = await axios.post(postURL, formData)
        return res
    }

    printDrumStickLabels = async (checkedIds, fileName) => {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

        const data = JSON.stringify(checkedIds)

        await axios.post('/api/export/downloadDrumSticksLabels.pdf', data, { headers: headers, responseType: 'blob' })
            .then(blob => {
                var url = window.URL.createObjectURL(blob.data)
                var a = document.createElement('a')
                a.href = url
                a.download = fileName
                document.body.appendChild(a)
                a.click()
                a.remove()
            })
    }

    fetchData = async (getURL) => {
        const res = await axios.get(getURL)
        return res
    }

    fetchEnumData = (enumURL) => {
        const res = axios.get(enumURL)
        return res
    }

    fetchDataItem = async (url, id) => {
        const res = await axios.get(url + `${id}`)
        return res
    }

    deleteItem = async (deleteURL, id) => {
        const res = await axios.delete(deleteURL + `${id}`)
        return res
    }

    postPutItem = async (url, id, item) => {
        let res
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
        const data = JSON.stringify(item)
        if (id === 'new') {
            res = await axios.post(url, data, { headers: headers })
        } else {
            res = await axios.put(url, data, { headers: headers })
        }
        return res
    }

}