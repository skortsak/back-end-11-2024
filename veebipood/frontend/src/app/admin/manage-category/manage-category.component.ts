import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-manage-category',
  standalone: true,
  imports: [FormsModule], // siia mida ei eksisteeri tavalises HTMLis
  templateUrl: './manage-category.component.html',
  styleUrl: './manage-category.component.css'
})
export class ManageCategoryComponent {
  categories: any[] = [];
  newCategory = "";

  constructor(private categoryService: CategoryService) {} // siia mdia ei eksisteeri tavalises JavaScriptis

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe(res =>
      this.categories = res
    );
  }

  addCategory() {
    this.categoryService.addCategory(this.newCategory).subscribe(res =>{
      this.categories = res;
      this.newCategory = "";
    });
  }

  deleteCategory(id: number) {
    this.categoryService.deleteCategory(id).subscribe(res =>
      this.categories = res
    );
  }
}
