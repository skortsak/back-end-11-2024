import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CategoryService } from '../../services/category.service';
import { TaskService } from '../../services/task.service';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent {
  categories: any[] = [];
  newCategory = "";
  params = {search: "", activeCategoryId: 0, currentPage: 0, pageSize: 6, sort: "title", direction: "asc"};
  message = "";

  constructor(private categoryService: CategoryService) {}

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
    this.categoryService.deleteCategory(id).subscribe({
      next: (res) => {
        this.categories = res
      },
      error: (message) => {
        console.log(message);
        this.message = message.error;
      }});
  }
}
