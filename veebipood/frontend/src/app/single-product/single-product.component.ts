import { Component } from '@angular/core';
import { Product } from '../models/Product';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-single-product',
  standalone: true,
  imports: [],
  templateUrl: './single-product.component.html',
  styleUrl: './single-product.component.css'
})
export class SingleProductComponent {
  product!: Product;

  constructor(private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {  // .get("") <---> /product/:
    const productName = this.route.snapshot.paramMap.get("productName");
    if (productName === null) {
      return;
    }
    this.productService.getProduct(productName).subscribe(res => {
      this.product = res;
    })
  }
}
