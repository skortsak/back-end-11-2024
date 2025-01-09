export interface Checkout {
    id: string;
    borrowerFirstName: string;
    borrowerLastName: string;
    borrowedBook: {
      id: string;
      title: string;
      author: string;
    };
    checkedOutDate: string;
    dueDate: string;
    returnedDate: string | null;
  }
  