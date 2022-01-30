module.exports = {
    moduleNameMapper: {
        '^.+.(svg|css)$': 'jest-transform-stub',
    },
    "testEnvironment": "jsdom",
    "setupFilesAfterEnv": [
        "<rootDir>/src/setuptests.ts"
    ]

}
