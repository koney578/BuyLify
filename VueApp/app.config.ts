export default defineAppConfig({ // TODO naprawic style do stronicowania bo nie dziala cos
    ui: {
        strategy: 'override',
        pagination : {

            default: {
                activeButton: {
                    color: 'rose-400',
                },
                lastButton: {
                    color: 'red',
                }
            }
        }
    }
})
