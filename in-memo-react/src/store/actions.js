export const INIT_LOCALIZED_MESSAGES = 'INIT_LOCALIZED_MESSAGES'

const initLocalizedMessages = () => {
    let messages = new LocalizedStrings({
        en: {
            fileNameCDs: 'CD.',
            fileNameDrumSticks: 'Drumsticks.',
            fileNameDrumSticksLabels: 'Drumsticks (labels).pdf',
        },
        es: {
            fileNameCDs: 'CD.',
            fileNameDrumSticks: 'Baquetas.',
            fileNameDrumSticksLabels: 'Baquetas (etiquetas).pdf',
        },
        ru: {
            fileNameCDs: 'Компакт диски.',
            fileNameDrumSticks: 'Барабанные палочки.',
            fileNameDrumSticksLabels: 'Барабанные палочки (лейблы).pdf',
        },
        uk: {
            fileNameCDs: 'Компакт диски.',
            fileNameDrumSticks: 'Барабанні палочки.',
            fileNameDrumSticksLabels: 'Барабанні палочки (лейбли).pdf',
        },
        ja: {
            fileNameCDs: 'CD.',
            fileNameDrumSticks: 'ドラムスティック.',
            fileNameDrumSticksLabels: 'ドラムスティック (ラベル).pdf',
        }
    })
    return {
        type: INIT_LOCALIZED_MESSAGES,
        payload: messages
    }
}

export {
    initLocalizedMessages
}