import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { Product } from '../models/Product';
import { CartProduct } from '../models/CartProduct';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})                        // teeb validatsiooni, et mul on ngOnInit funktsioon olemas ja õigel kujul
export class CartComponent implements OnInit {
  // private ette kui teda ei ole HTMLs
  //private CartLS: CartProduct[] = [];
  cart: {"product": Product, "quantity": number}[] = [];
  parcelMachines: any;

  constructor(private cartService: CartService,
    private productService: ProductService
  ) {}

  // käima minemise funktsioon -> kui HTMLs tulb nähtavale (läheb käima vahetult enne HTMLi näitamist)

  // Service-tel pole ngOnIniti võimalik panna
  ngOnInit(): void {
    // // kui pöördun funktsiooni sees ülemiste klassi muutujate poole, pean kasutama alati this.
    // const CartLS: CartProduct[] = this.cartService.getCart();
    // let array: {"product": Product, "quantity": number}[] = [];
    // CartLS.forEach(cartRow => {
    //   this.productService.getProduct(cartRow.productName).subscribe(res => {
    //     array.push({"product": res, "quantity": cartRow.quantity});
    //   });
    // });

    // this.cart = array;
    this.cart = this.cartService.getCart();
    this.getPMs("ee");
  }

  decreaseQuqantity(cartRow: {"product": Product, "quantity": number}) {
    this.cartService.decreaseQuqantity(cartRow);
  }

  increaseQuqantity(cartRow: {"product": Product, "quantity": number}) {
    this.cartService.increaseQuantity(cartRow);
  }

  deleteFromCart(index: number) {
    this.cartService.remove(index);
  }

  saveOrder() {
    this.cartService.saveOrder(1).subscribe(() => localStorage.removeItem("cart"));
    this.cart.splice(0); // kustutab alates 0ndast indexist, lõpuni välja
  }

  getPMs(country: string) {
    this.cartService.getParcelMachines(country).subscribe(data => {
      this.parcelMachines = data;
    })
  }

  calculateCartSum() {
    return this.cartService.calculateTotal();
  }
}
