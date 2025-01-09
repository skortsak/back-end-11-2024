import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Nutrients, Product } from '../../models/Product';
import { Category } from '../../models/Category';
import { Characteristic } from '../../models/Characteristic';

@Component({
  selector: 'app-edit-product',
  standalone: true,  // ReactiveForms --> need vormid mida luuakse, ts poole peal (FormGroup)
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.css'
})
export class EditProductComponent {
  editProductForm!: FormGroup;
  private product!: Product;

  constructor(private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    const productName = this.route.snapshot.paramMap.get("productName");
    if (productName) {
      this.productService.getProduct(productName).subscribe(res => {
        this.product = res;
        this.editProductForm = new FormGroup({
          //id: new FormControl(res.id),
          //nutrients: new FormControl(res.nutrients),
          //characteristic: new FormControl(res.characteristic),
          name: new FormControl(res.name),
          price: new FormControl(res.price),
          active: new FormControl(res.active),
          //category: new FormControl(res.category),
          image: new FormControl(res.image)
        })
      })
    }
  }


  onSubmit() {
    const val = this.editProductForm.value;
    const product = new Product(
          this.product.id, 
          val.name, val.price, val.active, val.image,
          this.product.nutrients,
          this.product.category,
          this.product.characteristic
        );
    this.productService.editProduct(product).subscribe();
  }
}
