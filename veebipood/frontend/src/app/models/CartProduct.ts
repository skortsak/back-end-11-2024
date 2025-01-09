import { Product } from "./Product";

export class CartProduct {
    constructor(
        public product: Product,
        public quantity: number
    ) {}
}

// export class CartProduct {
//     constructor(
//         public productName: string,  ---> convertima nime tooteks
//         public quantity: number
//     ) {}
// }