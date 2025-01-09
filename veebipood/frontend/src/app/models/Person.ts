export class Person {
    constructor(
        public id: number,
        public email: string,
        public password: string,
        public firstName: string,
        public lastName: string,
        public address: Address,
        public admin: boolean
    ) {}
}

export class Address {
    constructor(
        public street: string,
        public houseNo: string,
        public apatmentNo: string,
        public postalIndex: string,
        public city: string,
        public county: string,
        public country: string
    ) {}
}