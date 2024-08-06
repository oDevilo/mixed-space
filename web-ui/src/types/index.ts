export class TagView {
  id: string;
  name: string;
}

export class ObjectView {
  id: string;
  name: string;
  tags?: Array<TagView>;
  tagIds?: Array<string>;
}
