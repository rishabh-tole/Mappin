class ServoPositionsBuffer:
    def __init__(self, max_size=10):
        self.max_size = max_size
        self.buffer = []

    def add_position(self, position):
        if len(self.buffer) < self.max_size:
            self.buffer.append(position)
        else:
            # If buffer is full, discard the oldest position and append the new one
            self.buffer.pop(0)
            self.buffer.append(position)
            print("Buffer is full. Discarding oldest position and adding new position:", position)

    def get_newest_position(self):
        if self.buffer:
            newest_position = self.buffer[-1]
            self.buffer.clear()
            return newest_position
        else:
            return None

    def is_empty(self):
        return len(self.buffer) == 0

    def is_full(self):
        return len(self.buffer) == self.max_size

