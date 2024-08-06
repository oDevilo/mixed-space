export interface TagView {
  id: string;
  name: string;
}

export interface ObjectView {
  id: string;
  name: string;
  tags: Array<TagView> | [];
  tagIds: Array<string> | [];
}
