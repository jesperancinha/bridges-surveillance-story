module.exports = {
    moduleNameMapper: {
        '^.+.(svg|css)$': 'jest-transform-stub',
    },
    "setupFilesAfterEnv": [
        "<rootDir>/src/setupTests.ts"
    ]

}
